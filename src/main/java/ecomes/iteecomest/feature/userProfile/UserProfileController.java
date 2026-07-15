package ecomes.iteecomest.feature.userProfile;
import ecomes.iteecomest.feature.userProfile.dto.UpdateUserProfileRequest;
import ecomes.iteecomest.feature.userProfile.dto.UserProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user-profiles")
@RequiredArgsConstructor
public class UserProfileController {
    private final UserProfileService userProfileService;
    @GetMapping("/me")
    public UserProfileResponse me() {
        return userProfileService.me();
    }

    @PatchMapping("/me")
    public UserProfileResponse updateProfile(
            @RequestBody UpdateUserProfileRequest updateUserProfileRequest
    ) {
        return userProfileService.updateProfile(updateUserProfileRequest);
    }

}
