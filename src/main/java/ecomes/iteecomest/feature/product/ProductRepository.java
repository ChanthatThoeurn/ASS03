package ecomes.iteecomest.feature.product;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Boolean existsByName(String name);


    Optional<Product> findByCode(@NotBlank(message = "Code is required") String code);
}
