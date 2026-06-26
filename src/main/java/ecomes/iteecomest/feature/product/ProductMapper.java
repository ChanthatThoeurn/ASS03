package ecomes.iteecomest.feature.product;
import ecomes.iteecomest.feature.product.dto.CreateProductRequest;
import ecomes.iteecomest.feature.product.dto.ProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "unitPrice", target = "price")
    Product mapProductToDTO(CreateProductRequest request);
    ProductResponse mapDTOToResponse(Product product);
}
