package kitchenpos.products.tobe.fixture;

import kitchenpos.products.tobe.domain.Price;
import kitchenpos.products.tobe.domain.Product;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductFixture {

    public static Product createProductRequest() {
        return createProductRequest("후라이드", BigDecimal.valueOf(16_000L));
    }

    public static Product createProductRequest(final String name, final long price) {
        return createProductRequest(name, BigDecimal.valueOf(price));
    }

    public static Product createProductRequest(final String name, final BigDecimal price) {
        final Product product = new Product();
        product.setId(UUID.randomUUID());
        product.setName(name);
        product.setPrice(new Price(price));
        return product;
    }
}
