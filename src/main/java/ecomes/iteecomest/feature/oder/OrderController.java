package ecomes.iteecomest.feature.oder;
import ecomes.iteecomest.feature.oder.dto.CreateOrderRequest;
import ecomes.iteecomest.feature.oder.dto.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public OrderResponse createNew(@RequestBody CreateOrderRequest request){

        return orderService.createOrder(request);
    }

    @GetMapping
    public Page<OrderResponse> getOrders(
            @RequestParam(required = false,defaultValue = "0") Integer pageNumber,
            @RequestParam(required = false,defaultValue = "25") Integer pageSize){
        return orderService.findAllOrders(pageNumber,pageSize);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public OrderResponse findOrderById(@PathVariable UUID id){
        return orderService.findOrderById(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping ("/{id}")
    public OrderResponse softDelete(@PathVariable UUID id){
        return orderService.softDeleteOrderById(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void hardDelete(@PathVariable UUID id){
         orderService.hardDeleteOrderById(id);
    }

    @PutMapping ("/{id}/payment")
    public OrderResponse paymentStatus( @PathVariable UUID id){
        return orderService.setPaymentStatus(id);
    }
}
