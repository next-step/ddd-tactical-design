package kitchenpos.fixture;

import java.math.BigDecimal;
import kitchenpos.products.domain.tobe.Product;
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

    public static Product createFired() {
        return createFired(20_000L);
    }

    public static Product createFired(Long price) {
        return new Product("후라이드", BigDecimal.valueOf(price));
    }

    public static Product createSeasoned() {
        return new Product("양념", BigDecimal.valueOf(22_000L));
    }
}
