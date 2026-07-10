package ecomes.iteecomest.feature.oder;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface OrderRepository extends CrudRepository<OrderLine, UUID> {
}
