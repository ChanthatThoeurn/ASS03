package ecomes.iteecomest.feature.oder;

import ecomes.iteecomest.feature.oder.dto.CreateOrderRequest;
import ecomes.iteecomest.feature.oder.dto.OrderResponse;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.data.domain.Page;

import java.util.UUID;


public interface OrderService {

    /**
     * Writing by Chanthat
     * No AI
     * @param request
     * @return
     */
    OrderResponse createOrder( CreateOrderRequest request);
    Page<OrderResponse> findAllOrders(int pageNumber, int pageSize);
    OrderResponse findOrderById(UUID id);
    OrderResponse softDeleteOrderById(UUID id);
    void hardDeleteOrderById(UUID id);
    OrderResponse setPaymentStatus(UUID status);
}
