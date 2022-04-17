package kitchenpos.products.application;

import java.util.List;
import java.util.UUID;
import kitchenpos.products.domain.ProductService;
import kitchenpos.products.domain.dtos.ProductCommand;
import kitchenpos.products.domain.dtos.ProductInfo;
import org.springframework.stereotype.Component;

@Component
public class ProductFacade {

    private final ProductService productService;

    public ProductFacade(ProductService productService) {
        this.productService = productService;
    }

    public ProductInfo createProduct(final ProductCommand.RegisterProductCommand command) {
        return productService.create(command);
    }

    public ProductInfo changePrice(final UUID productId, final ProductCommand.ChangePriceCommand command) {
        return productService.changePrice(productId, command);
    }

    public List<ProductInfo> findAll() {
        return productService.findAll();
    }
}
