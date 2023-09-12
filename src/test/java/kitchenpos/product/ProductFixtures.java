package kitchenpos.product;

import kitchenpos.product.adapter.out.persistence.ProductEntity;
import kitchenpos.product.domain.Product;
import kitchenpos.product.domain.ProductId;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductFixtures {
    private ProductFixtures() {}

    public static Product product() {
        return product("후라이드", 16_000L);
    }

    public static Product product(final String name, final long price) {
        return new Product(ProductId.newId(), name, BigDecimal.valueOf(price));
    }

    public static Product product(final String name, final BigDecimal price) {
        return new Product(ProductId.newId(), name, price);
    }
}
