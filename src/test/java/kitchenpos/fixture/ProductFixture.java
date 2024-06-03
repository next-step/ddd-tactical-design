package kitchenpos.fixture;

import java.math.BigDecimal;
import java.util.UUID;
import kitchenpos.products.domain.Product;
import kitchenpos.products.ui.dto.ProductCreateRequest;

public class ProductFixture {

    public static ProductCreateRequest createRequest(final String name) {
        return createRequest(name, 20_000L);
    }

    public static ProductCreateRequest createRequest(final long price) {
        return createRequest("후라이드", price);
    }

    public static ProductCreateRequest createRequest(final String name, final long price) {
        return new ProductCreateRequest(name, BigDecimal.valueOf(price));
    }

    public static Product changePriceRequest(final Long price) {
        Product product = new Product();
        product.setPrice(BigDecimal.valueOf(price));
        return product;
    }

    public static Product createFired() {
        Product product = new Product();
        product.setId(UUID.randomUUID());
        product.setPrice(BigDecimal.valueOf(20_000L));
        product.setName("후라이드");
        return product;
    }
}
