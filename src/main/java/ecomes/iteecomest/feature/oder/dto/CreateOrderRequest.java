package ecomes.iteecomest.feature.oder.dto;


import jakarta.validation.constraints.*;

import java.util.List;

public record CreateOrderRequest(
        @NotBlank(message = "Address is required")
        String address,
        @NotNull(message = "Discord is required")
        @Min(0)
        @Max(100)
        Float discord,
        @Size(max = 255)
        String remark,

        @NotEmpty(message = "Order is required")
        List<OrderLineDto> orderLists
) {
}
