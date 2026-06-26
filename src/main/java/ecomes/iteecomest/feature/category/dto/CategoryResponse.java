package ecomes.iteecomest.feature.category.dto;

import lombok.Builder;
import java.util.List;

@Builder
public record CategoryResponse(
        Integer id,
        String name,
        String description,
        String icon,
        Boolean isDeleted,
        Integer parentCategory,
        List<CategoryResponse> subcategories
) {
}