package ecomes.iteecomest.feature.auth;

import ecomes.iteecomest.feature.auth.dto.RegisterResponse;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class RegisterMapper {

    public RegisterResponse mapUserRepresentaionToRegisterResponse(
            UserRepresentation userRepresentation
    ) {
        return RegisterResponse.builder()
                .userId(userRepresentation.getId())
                .userName(userRepresentation.getUsername())
                .email(userRepresentation.getEmail())
                .firstName(userRepresentation.getFirstName())
                .lastName(userRepresentation.getLastName())

                .build();
    }

}