package ecomes.iteecomest.feature.category;
import ecomes.iteecomest.feature.category.dto.CategoryResponse;
import ecomes.iteecomest.feature.category.dto.CreateCategoryRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")

public interface CategoryMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "parentCategory", ignore = true)
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    Category createCategoryRequestToCategory(CreateCategoryRequest createCategoryRequest);

    @Mapping(target = "parentCategory", expression = "java(category.getParentCategory() != null ? category.getParentCategory().getId() : null)")
    @Mapping(target = "subcategories", ignore = true)
    CategoryResponse categoryToResponse(Category category);
}