package kitchenpos.product.application.facade;

import java.util.List;
import java.util.UUID;
import kitchenpos.product.application.dto.ProductRequest;
import kitchenpos.product.application.dto.ProductResponse;
import kitchenpos.product.domain.entity.Product;
import kitchenpos.product.domain.service.ProductPurgomalumClient;
import kitchenpos.product.domain.service.ProductService;
import org.springframework.stereotype.Component;

@Component
public class ProductFacade {

    private final ProductService productService;

    public ProductFacade(
        ProductService productService
    ) {
        this.productService = productService;
    }

    public ProductResponse create(ProductRequest.Create request) {
        return productService.create(request);
    }

    public ProductResponse changePrice(ProductRequest.UpdatePrice request) {
        return productService.changePrice(request);
    }

    public List<ProductRequest> findAll() {
       return productService.findAll();
    }
}
