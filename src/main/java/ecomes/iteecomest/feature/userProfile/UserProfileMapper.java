package ecomes.iteecomest.feature.userProfile;
import ecomes.iteecomest.feature.userProfile.dto.UpdateUserProfileRequest;
import ecomes.iteecomest.feature.userProfile.dto.UserProfileResponse;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public abstract class UserProfileMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void mapUpdateUserProfileRequestToUserProfile(
            UpdateUserProfileRequest updateUserProfileRequest,
            @MappingTarget UserProfile userProfile
    );

    public void mapUpdateUserProfileRequestToUserRepresentation(
            UpdateUserProfileRequest updateUserProfileRequest,
            @MappingTarget UserRepresentation userRepresentation
    ) {
        if (updateUserProfileRequest.firstName() != null)
            userRepresentation.setFirstName(updateUserProfileRequest.firstName());

        if (updateUserProfileRequest.lastName() != null)
            userRepresentation.setLastName(updateUserProfileRequest.lastName());

    }
    public UserProfileResponse mapUserRepresentationToUserProfileResponse(UserRepresentation userRepresentation, UserProfile userProfile) {
        return UserProfileResponse.builder()
                .userId(userRepresentation.getId())
                .username(userRepresentation.getUsername())
                .email(userRepresentation.getEmail())
                .firstName(userRepresentation.getFirstName())
                .lastName(userRepresentation.getLastName())
                .build();
    }

}