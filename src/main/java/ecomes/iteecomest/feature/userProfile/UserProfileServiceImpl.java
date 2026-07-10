package ecomes.iteecomest.feature.userProfile;

import ecomes.iteecomest.feature.userProfile.dto.UserProfileResponse;
import ecomes.iteecomest.securityConfig.AuthUtils;
import ecomes.iteecomest.securityConfig.KeycloakAdminClientProps;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class UserProfileServiceImpl implements UserProfileService {
    private final Keycloak keycloak;
    private final KeycloakAdminClientProps props;
    private final UserProfileMapper userProfileMapper;

    @Override
    public UserProfileResponse me() {
        // 1. Profile from Keycloak by userId
        String userId = AuthUtils.extractUserId();
        UserRepresentation keycloakUser = keycloak.realm(props.getTargetRealm())
                .users()
                .get(userId)
                .toRepresentation();

        // 2. Profile from Database by userId
        return userProfileMapper.mapUserRepresentationToUserProfileResponse(keycloakUser);
    }
}
