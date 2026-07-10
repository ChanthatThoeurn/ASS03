package ecomes.iteecomest.feature.oder;
import ecomes.iteecomest.feature.oder.dto.CreateOrderRequest;
import ecomes.iteecomest.feature.oder.dto.OrderResponse;
import org.mapstruct.Mapper;
@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order mapCreateOrderRequestToOrder(CreateOrderRequest createOrderRequest);
    OrderResponse mapOrderToOrderResponse(Order oder);
}
