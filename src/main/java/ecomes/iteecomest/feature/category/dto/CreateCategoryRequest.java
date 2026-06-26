package ecomes.iteecomest.feature.category.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateCategoryRequest(
        @NotBlank(message = "Name is required")
        @Column(nullable = false, unique = true)
        String name,
        String description,
        @Size(max = 255)
        String icon,
        Integer parentCategory,  // ✅ just the ID
        Boolean deleted
) {
}