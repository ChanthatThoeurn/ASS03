package ecomes.iteecomest.feature.oder;
import ecomes.iteecomest.feature.oder.dto.CreateOrderRequest;
import ecomes.iteecomest.feature.oder.dto.OrderResponse;
import ecomes.iteecomest.feature.product.Product;
import ecomes.iteecomest.feature.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;
    @Override
    public OrderResponse createOrder(CreateOrderRequest request) {
      final  Oder oder = orderMapper.toOrderRequest(request);
        oder.setRemark(request.remark());
        List<OrderLine> orderLines = new ArrayList<>();
      boolean isValidOrder =  request.orderLists().stream()
                .allMatch(orderLineDto->{
                    Optional<Product> productOptional = productRepository
                            .findByCode(orderLineDto.code());
                    if(productOptional.isPresent()) {
                        OrderLine orderLine = new OrderLine();
                        orderLine.setProduct(productOptional.get());
                        orderLine.setQty(orderLineDto.qty());
                        orderLine.setOrder(oder);
                        orderLine.setUnitPrice(orderLineDto.unitPrice());
                        orderLines.add(orderLine);
                        return true;
                    }
                    return false;

                });
      if(!isValidOrder) {
          throw new ResponseStatusException(
                  HttpStatus.BAD_REQUEST, "Invalid order code");
      }
      oder.setCustomerId("ISTAD");
      oder.setOrderLines(orderLines);
      oder.setDeleted(false);
      oder.setCreatedAt(LocalDateTime.now());
      oder.setStatus(false);
      Oder saveOrder = orderRepository.save(oder);
        return orderMapper.toOrderResponse(saveOrder);
    }

    @Override
    public Page<OrderResponse> findAllOrders(int pageNumber, int pageSize) {
     Sort sortById = Sort.by(Sort.Direction.DESC, "id");
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sortById);
        Page<Oder> orders = orderRepository.findAll(pageRequest);


        return orders.map(orderMapper::toOrderResponse);
    }

    @Override
    public OrderResponse findOrderById(UUID id) {

        return orderMapper.toOrderResponse(
                orderRepository.findById(id)
                        .orElseThrow(()
                        -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Order not found"))
        );
    }

    @Override
    public OrderResponse softDeleteOrderById(UUID id) {
       Oder order = orderRepository.findById(id)
               .orElseThrow(()-> new ResponseStatusException(
                       HttpStatus.NOT_FOUND, "Order not found"));
                order.setDeleted(true);
        return orderMapper.toOrderResponse(orderRepository.save(order));
    }

    @Override
    public void hardDeleteOrderById(UUID id) {
        Oder order = orderRepository.findById(id)
                .orElseThrow(()->
                        new  ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
                orderRepository.delete(order);
    }

    @Override
    public OrderResponse setPaymentStatus(UUID status) {
       Oder order = orderRepository.findById(status)
               .orElseThrow(()->new ResponseStatusException(HttpStatus.NO_CONTENT, "Order not found"));
       order.setStatus(true);
        return orderMapper.toOrderResponse(orderRepository.save(order));
    }
}
