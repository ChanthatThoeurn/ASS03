package ecomes.iteecomest.feature.oder.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record OrderLineDto(
       @NotBlank(message = "Code is required")
        String code,
        @Positive
        @NotNull(message = "qty is required")
        Integer qty,
        @NotNull(message = "unite price is required")
        BigDecimal unitPrice
) {
}
