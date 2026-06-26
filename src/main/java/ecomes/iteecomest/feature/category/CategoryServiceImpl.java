package ecomes.iteecomest.feature.category;
import ecomes.iteecomest.feature.category.dto.CategoryResponse;
import ecomes.iteecomest.feature.category.dto.CreateCategoryRequest;
import ecomes.iteecomest.feature.category.dto.UpdateCategoryRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryResponse createCategory(CreateCategoryRequest request) {
        log.info("Create Category Request: {}", request);
        if (categoryRepository.existsCategoriesByName(request.name())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Category already exists");
        }
        Category parentCategory = null;
        if (request.parentCategory() != null) {
            parentCategory = categoryRepository.findById(request.parentCategory())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Parent Category Not Found"));
        }
        Category category = categoryMapper.createCategoryRequestToCategory(request);
        category.setParentCategory(parentCategory);
        return categoryMapper.categoryToResponse(categoryRepository.save(category));
    }

    @Override
    public Page<CategoryResponse> getAllCategories(int pageNumber, int pageSize) {
        return categoryRepository.findAllByIsDeletedFalse(PageRequest.of(pageNumber, pageSize))
                .map(categoryMapper::categoryToResponse);
    }

    @Override
    public List<CategoryResponse> getAllCategoriesNoPagination() {
        return categoryRepository.findAllByIsDeletedFalse()
                .stream().map(categoryMapper::categoryToResponse).collect(Collectors.toList());
    }

    @Override
    public CategoryResponse getCategoryById(Integer id) {
        return categoryMapper.categoryToResponse(
                categoryRepository.findById(id)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category Not Found"))
        );
    }

    @Override
    public List<CategoryResponse> getSubcategories(Integer parentId) {
        categoryRepository.findById(parentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Parent Category Not Found"));
        return categoryRepository.findAllByParentCategoryIdAndIsDeletedFalse(parentId)
                .stream().map(categoryMapper::categoryToResponse).collect(Collectors.toList());
    }

    @Override
    public List<CategoryResponse> getCategoryTree() {
        List<Category> roots = categoryRepository.findAllByParentCategoryIsNullAndIsDeletedFalse();
        return roots.stream().map(this::buildTree).collect(Collectors.toList());
    }

    private CategoryResponse buildTree(Category category) {
        List<CategoryResponse> subcategories = categoryRepository
                .findAllByParentCategoryIdAndIsDeletedFalse(category.getId())
                .stream().map(this::buildTree).collect(Collectors.toList());
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .icon(category.getIcon())
                .isDeleted(category.getIsDeleted())
                .parentCategory(category.getParentCategory() != null ? category.getParentCategory().getId() : null)
                .subcategories(subcategories)
                .build();
    }

    @Override
    public void hardDeleteCategory(Integer id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category Not Found"));
        categoryRepository.delete(category);
    }

    @Override
    public CategoryResponse softDeleteCategory(Integer id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category Not Found"));
        category.setIsDeleted(true);
        return categoryMapper.categoryToResponse(categoryRepository.save(category));
    }

    @Override
    public CategoryResponse restoreCategory(Integer id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category Not Found"));
        category.setIsDeleted(false);
        return categoryMapper.categoryToResponse(categoryRepository.save(category));
    }

    @Override
    public CategoryResponse updateCategory(Integer id, UpdateCategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category Not Found"));
        if (request.name() != null) category.setName(request.name());
        if (request.description() != null) category.setDescription(request.description());
        if (request.icon() != null) category.setIcon(request.icon());
        return categoryMapper.categoryToResponse(categoryRepository.save(category));
    }

    @Override
    public Page<CategoryResponse> searchCategories(String keyword, int pageNumber, int pageSize) {
        return categoryRepository.findAllByNameContainingIgnoreCaseAndIsDeletedFalse(
                        keyword, PageRequest.of(pageNumber, pageSize))
                .map(categoryMapper::categoryToResponse);
    }

    @Override
    public CategoryResponse moveCategory(Integer id, Integer newParentId) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category Not Found"));
        Category newParent = categoryRepository.findById(newParentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "New Parent Category Not Found"));
        if (newParentId.equals(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category cannot be its own parent");
        }
        category.setParentCategory(newParent);
        return categoryMapper.categoryToResponse(categoryRepository.save(category));
    }

    @Override
    public void bulkDeleteCategories(List<Integer> ids) {
        List<Category> categories = categoryRepository.findAllById(ids);
        if (categories.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No categories found");
        }
        categories.forEach(c -> c.setIsDeleted(true));
        categoryRepository.saveAll(categories);
    }

    @Override
    public Integer getProductCount(Integer id) {
        categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category Not Found"));
        return categoryRepository.countProductsByCategoryId(id);
    }
}