package ecomes.iteecomest.feature.oder.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record OrderLineDto(
        @NotBlank(message = "Code is required")
        String code,
        @Positive
        @NotNull(message = "Qty is required")
        Integer qty,
        @NotNull(message = "Unit price is required")
        @Positive
        BigDecimal unitPrice
) {
}