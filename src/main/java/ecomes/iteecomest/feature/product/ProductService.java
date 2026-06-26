package ecomes.iteecomest.feature.product;
import ecomes.iteecomest.feature.product.dto.CreateProductRequest;
import ecomes.iteecomest.feature.product.dto.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    /**
     * find product by page gernation
     * @param CreateProductRequest is requesting data for creating product
     * @return {@link ProductResponse}
     * @author thoeurn_chanthat
     * @since 23-june-2026
     */
    Page<ProductResponse> findAll(int pageNumber, int PageSize);
    /**
     * create new product
     * @param CreateProductRequest is requesting data for creating product
     * @return {@link ProductResponse}
     * @author thoeurn_chanthat
     * @since 23-june-2026
     */
    ProductResponse CreateProducts(CreateProductRequest request);



}
