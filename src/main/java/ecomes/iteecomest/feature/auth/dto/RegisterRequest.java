package ecomes.iteecomest.feature.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record RegisterRequest(
        @NotBlank
        String userName,
        @NotBlank
        String password,
        @NotBlank
        String confirmPassword,
        @NotBlank
        String email,
        @NotBlank
        String fistName,
        @NotBlank
        String lastName
) {
}
