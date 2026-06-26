package ecomes.iteecomest.feature.oder;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OderRepository extends JpaRepository<Oder, Integer> {
    Optional<Oder> findById(UUID id);
    Optional<Oder> findByStatus(Boolean status);


}
