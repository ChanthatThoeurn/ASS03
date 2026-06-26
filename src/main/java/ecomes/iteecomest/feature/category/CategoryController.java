package ecomes.iteecomest.feature.category;
import ecomes.iteecomest.feature.category.dto.CategoryResponse;
import ecomes.iteecomest.feature.category.dto.CreateCategoryRequest;
import ecomes.iteecomest.feature.category.dto.UpdateCategoryRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CategoryResponse create(@Valid @RequestBody CreateCategoryRequest request) {
        return categoryService.createCategory(request);
    }
    @GetMapping
    public Page<CategoryResponse> getAllCategories(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "25") int pageSize) {
        return categoryService.getAllCategories(pageNumber, pageSize);
    }
    @GetMapping("/all")
    public List<CategoryResponse> getAllCategoriesNoPagination() {
        return categoryService.getAllCategoriesNoPagination();
    }

    @GetMapping("/{id}")
    public CategoryResponse getCategoryById(@PathVariable Integer id) {
        return categoryService.getCategoryById(id);
    }

    @GetMapping("/{id}/subcategories")
    public List<CategoryResponse> getSubcategories(@PathVariable Integer id) {
        return categoryService.getSubcategories(id);
    }

    @GetMapping("/tree")
    public List<CategoryResponse> getCategoryTree() {
        return categoryService.getCategoryTree();
    }

    @GetMapping("/search")
    public Page<CategoryResponse> searchCategories(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "25") int pageSize) {
        return categoryService.searchCategories(keyword, pageNumber, pageSize);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void hardDelete(@PathVariable Integer id) {
        categoryService.hardDeleteCategory(id);
    }

    @PutMapping("/{id}")
    public CategoryResponse softDelete(@PathVariable Integer id) {
        return categoryService.softDeleteCategory(id);
    }

    @PatchMapping("/{id}")
    public CategoryResponse updateCategory(@PathVariable Integer id,
                                           @Valid @RequestBody UpdateCategoryRequest request) {
        return categoryService.updateCategory(id, request);
    }

    @PatchMapping("/{id}/move")
    public CategoryResponse moveCategory(@PathVariable Integer id,
                                         @RequestParam Integer newParentId) {
        return categoryService.moveCategory(id, newParentId);
    }

    @PatchMapping("/{id}/restore")
    public CategoryResponse restoreCategory(@PathVariable Integer id) {
        return categoryService.restoreCategory(id);
    }

    @DeleteMapping("/bulk")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void bulkDelete(@RequestBody List<Integer> ids) {
        categoryService.bulkDeleteCategories(ids);
    }

    @GetMapping("/{id}/product-count")
    public Integer getProductCount(@PathVariable Integer id) {
        return categoryService.getProductCount(id);
    }
}