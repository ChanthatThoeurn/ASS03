package ecomes.iteecomest.feature.auth;

import ecomes.iteecomest.feature.auth.dto.RegisterRequest;
import ecomes.iteecomest.feature.auth.dto.RegisterResponse;
import ecomes.iteecomest.feature.userProfile.UserProfile;
import ecomes.iteecomest.feature.userProfile.UserProfileRepository;
import ecomes.iteecomest.securityConfig.KeycloakAdminClientProps;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegisterServiceImpl implements RegisterService {

    private final Keycloak keycloak;
    private final KeycloakAdminClientProps props;
    private final RegisterMapper authMapper;
    private final UserProfileRepository userProfileRepository;

    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {

        // Validate password matching
        if (!registerRequest.password().equals(registerRequest.confirmPassword())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Passwords don't match!"
            );
        }

        // Create keycloak user
        UsersResource usersResource = keycloak
                .realm(props.getTargetRealm())
                .users();

        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(registerRequest.userName());
        userRepresentation.setEmail(registerRequest.email());
        userRepresentation.setFirstName(registerRequest.fistName());
        userRepresentation.setLastName(registerRequest.lastName());
        userRepresentation.setEnabled(true);
        userRepresentation.setEmailVerified(false);

        // Set Keycloak custom attributes
        Map<String, List<String>> attributes = new HashMap<>();
        userRepresentation.setAttributes(attributes);

        // Set Credential
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(registerRequest.password());
        userRepresentation.setCredentials(List.of(credential));

        try (Response response = usersResource.create(userRepresentation)) {
            log.info("Response status code: {}", response.getStatus());
            if (response.getStatus() == HttpStatus.CREATED.value()) {
                // Succeed situation
                UserRepresentation createdUser = keycloak.realm(props.getTargetRealm())
                        .users()
                        .search(userRepresentation.getUsername())
                        .getFirst();
                UserResource userResourceSet = keycloak.realm(props.getTargetRealm())
                        .users().get(createdUser.getId());
                userResourceSet.sendVerifyEmail();

                RoleRepresentation roleUser = keycloak.realm(props.getTargetRealm())
                        .roles().get(Role.USER.name()).toRepresentation();
                RoleRepresentation roleCustomer = keycloak.realm(props.getTargetRealm())
                        .roles().get(Role.CUSTOMER.name()).toRepresentation();
                userResourceSet.roles().realmLevel().add(List.of(roleUser,roleCustomer));

                log.info("Created user: {}", createdUser.getId());

                // Save user profile
                UserProfile userProfile = new UserProfile();
                userProfile.setUserId(createdUser.getId());
                userProfileRepository.save(userProfile);

                return authMapper.mapUserRepresentaionToRegisterResponse(createdUser);
            } else if (response.getStatus() == HttpStatus.CONFLICT.value()) {
                // Conflict situation
                log.info("Check username or email already exists!");
            }
        }

        return null;
    }
}