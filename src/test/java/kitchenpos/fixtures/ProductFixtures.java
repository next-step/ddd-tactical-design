package kitchenpos.fixtures;

import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductName;
import kitchenpos.products.tobe.domain.ProductPrice;

import java.math.BigDecimal;

public class ProductFixtures {

    public static Product createProduct(final String name, final BigDecimal price) {
        Product product = new Product(new ProductName(name), new ProductPrice(price));
        return product;
    }

    /**
     * OrderServiceTest
     */
    public static Product product() {
        return product("후라이드치킨", 16_000L);
    }

    public static Product product(final String name, final long price) {
        final Product product = new Product(new ProductName(name), new ProductPrice(BigDecimal.valueOf(price)));
        return product;
    }
}
