package ecomes.iteecomest.feature.auth.dto;

import lombok.Builder;

@Builder
public record RegisterResponse(
        String userId,
        String userName,
        String email,
        String firstName,
        String lastName
) {
}
