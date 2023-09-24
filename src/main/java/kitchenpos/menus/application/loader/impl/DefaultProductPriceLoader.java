package kitchenpos.menus.application.loader.impl;

import kitchenpos.common.domain.Price;
import kitchenpos.menus.application.loader.ProductPriceLoader;
import kitchenpos.products.application.ProductService;
import kitchenpos.products.application.dto.ProductResponse;
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
        ProductResponse product = productService.findProductById(productId);
        return new Price(product.getPrice());
    }
}
