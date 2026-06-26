package ecomes.iteecomest.feature.product.dto;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
public record CreateProductRequest(
        @NotBlank(message = "Name is required")
        @Size(max = 255)
        @Column(nullable = false, unique = true)
        String name,
        @Size(max = 255)
        String description,
        @Size(max = 255)
        String thumbnail,
        @NotNull(message = "Unite price is required")
        @Min(0)
        BigDecimal unitPrice,
        @NotNull(message = "Unite qty is required")
        @Min(0)
        Integer qty,
        @NotNull(message = "Category is required")
        @Positive
        Integer categoryId
) {
}