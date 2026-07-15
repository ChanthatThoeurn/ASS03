package ecomes.iteecomest.feature.userProfile;
import ecomes.iteecomest.feature.userProfile.dto.UpdateUserProfileRequest;
import ecomes.iteecomest.feature.userProfile.dto.UserProfileResponse;

public interface UserProfileService {
    UserProfileResponse updateProfile(
            UpdateUserProfileRequest updateUserProfileRequest);

            UserProfileResponse me();
}
