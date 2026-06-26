package ecomes.iteecomest.feature.oder.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record OrderResponse(
        UUID id,
        String customerId,
        String address,
        Float discord,
        String remark,
        Boolean status,
        LocalDateTime orderAt,
        Boolean isDeleted
) {
}
