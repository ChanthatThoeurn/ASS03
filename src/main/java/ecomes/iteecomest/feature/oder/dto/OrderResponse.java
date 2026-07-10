package ecomes.iteecomest.feature.oder.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record OrderResponse(
        UUID id,
        String customerId,
        String address,
        Float discount,
        String remark,
        Boolean status,
        LocalDateTime orderedAt,
        Boolean isDeleted
) {
}
