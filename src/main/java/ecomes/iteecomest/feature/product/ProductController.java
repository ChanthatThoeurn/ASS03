package ecomes.iteecomest.feature.product;
import ecomes.iteecomest.feature.product.dto.CreateProductRequest;
import ecomes.iteecomest.feature.product.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/products")
public class ProductController {
    private final ProductService productService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ProductResponse createProduct(@RequestBody CreateProductRequest product) {
        return productService.CreateProducts(product);
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<ProductResponse> findAll(
            @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(required = false,defaultValue = "25") Integer PageSize) {
        return productService.findAll(pageNumber,PageSize);
    }

}
