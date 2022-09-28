package kitchenpos.tobe;

import java.math.BigDecimal;
import java.util.UUID;
import kitchenpos.products.domain.FakeProductProfanityCheckClient;
import kitchenpos.products.domain.Product;
import kitchenpos.products.domain.ProductName;
import kitchenpos.products.domain.ProductPrice;

public class Fixtures {

    public static final UUID INVALID_ID = new UUID(0, 0);

    public static Product product() {
        return product("후라이드", 16_000L);
    }

    public static Product product(String name, long price) {
        return product(UUID.randomUUID(), name, price);
    }

    public static Product product(UUID id, String name, long price) {
        ProductName productName = new ProductName(name, new FakeProductProfanityCheckClient());
        ProductPrice productPrice = new ProductPrice(BigDecimal.valueOf(price));
        return new Product(id, productName, productPrice);
    }
}
