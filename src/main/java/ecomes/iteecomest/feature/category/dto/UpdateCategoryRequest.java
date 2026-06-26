package ecomes.iteecomest.feature.category.dto;

import jakarta.validation.constraints.Size;

public record UpdateCategoryRequest(
        String name,
        String description,
        @Size(max = 255)
        String icon
) {
}