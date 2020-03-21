package kitchenpos.menus.tobe.infrastructure;

import java.math.BigDecimal;
import kitchenpos.products.tobe.domain.usecase.ProductService;
import org.springframework.stereotype.Component;

@Component
public class ProductPriceManager {

    private final ProductService productService;

    public ProductPriceManager(ProductService productService) {
        this.productService = productService;
    }

    public BigDecimal getPrice(Long id) {
        return productService.get(id).getPrice();
    }
}
