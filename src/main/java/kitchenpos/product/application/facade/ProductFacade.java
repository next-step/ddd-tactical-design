package kitchenpos.product.application.facade;

import kitchenpos.product.domain.service.ProductPurgomalumClient;
import kitchenpos.product.domain.service.ProductService;
import org.springframework.stereotype.Component;

@Component
public class ProductFacade {

    private final ProductService productService;
    private final ProductPurgomalumClient productPurgomalumClient;

    public ProductFacade(ProductService productService,
        ProductPurgomalumClient productPurgomalumClient) {
        this.productService = productService;
        this.productPurgomalumClient = productPurgomalumClient;
    }
}
