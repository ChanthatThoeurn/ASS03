package ecomes.iteecomest.feature.category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    boolean existsCategoriesByName(String name);
    // Get all by pagination (not deleted)
    Page<Category> findAllByIsDeletedFalse(Pageable pageable);
    // Get all (no pagination, not deleted)
    List<Category> findAllByIsDeletedFalse();
    // Search by name
    Page<Category> findAllByNameContainingIgnoreCaseAndIsDeletedFalse(String keyword, Pageable pageable);
    // Get subcategories by parent ID
    List<Category> findAllByParentCategoryIdAndIsDeletedFalse(Integer parentId);
    // Get root categories (no parent) for tree
    List<Category> findAllByParentCategoryIsNullAndIsDeletedFalse();
    // Count products in category
    @Query("SELECT COUNT(p) FROM Product p WHERE p.category.id = :categoryId")
    Integer countProductsByCategoryId(Integer categoryId);
}