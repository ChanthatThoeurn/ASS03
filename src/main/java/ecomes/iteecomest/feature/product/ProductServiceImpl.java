package ecomes.iteecomest.feature.product;

import ecomes.iteecomest.feature.category.Category;
import ecomes.iteecomest.feature.category.CategoryRepository;
import ecomes.iteecomest.feature.product.dto.CreateProductRequest;
import ecomes.iteecomest.feature.product.dto.ProductResponse;
import ecomes.iteecomest.util.GenerateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @Override
    public Page<ProductResponse> findAll(int pageNumber, int PageSize) {
        Sort sortById = Sort.by(Sort.Direction.DESC, "id");
        PageRequest pageRequest = PageRequest.of(pageNumber, PageSize, sortById);

        Page<Product> productPage = productRepository
                .findAll(pageRequest);
        return productPage
                .map(productMapper::mapDTOToResponse);
    }

    @Override
    public ProductResponse CreateProducts(CreateProductRequest request) {
         //Step 1 : validation
         if(productRepository.existsByName(request.name())){
             throw new ResponseStatusException(HttpStatus.CONFLICT,
                     "Product name has already been exists");
         }
         // validation category
        Category category = categoryRepository.findById(request.categoryId()).
                orElseThrow(()->
                        new ResponseStatusException(HttpStatus.CONFLICT));
          // map data to dto
        Product product = productMapper.mapProductToDTO(request);
        product.setPrice(request.unitPrice());
        product.setCategory(category);
        product.setCode(GenerateUtils.generateProductCode());
        product.setSlug(GenerateUtils.generateSlug(request.name()));
        product.setIsAvailable(true);
        product.setIsDeleted(false);

        // map dto to data
        product = productRepository.save(product);
        return productMapper.mapDTOToResponse(product);
    }
}
