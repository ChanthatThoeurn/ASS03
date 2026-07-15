package ecomes.iteecomest.feature.userProfile.dto;

import lombok.Builder;

@Builder
public record UpdateUserProfileRequest(
        String firstName,
        String lastName,
        String gender,
        String address,
        String biography,
        String profilePicture
) {
}
