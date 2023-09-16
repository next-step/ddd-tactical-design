package kitchenpos.menus.infra;

import kitchenpos.common.domain.Price;
import kitchenpos.menus.application.ProductPriceLoader;
import kitchenpos.products.application.ProductService;
import kitchenpos.products.tobe.domain.ProductId;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DefaultProductPriceLoader implements ProductPriceLoader {

    private final ProductService productService;

    public DefaultProductPriceLoader(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public Price findPriceById(UUID productId) {
        return productService.findById(new ProductId(productId))
                .getPrice();
    }
}
