package kitchenpos.products;

import java.math.BigDecimal;
import java.util.UUID;
import kitchenpos.profanity.infra.FakeProfanityCheckClient;
import kitchenpos.products.domain.Product;
import kitchenpos.products.domain.ProductName;
import kitchenpos.products.domain.ProductPrice;

public class ProductFixtures {

    public static final UUID INVALID_ID = new UUID(0, 0);

    public static Product product() {
        return product("후라이드", 16_000L);
    }

    public static Product product(String name, long price) {
        return product(UUID.randomUUID(), name, price);
    }

    public static Product product(UUID id, String name, long price) {
        ProductName productName = new ProductName(name, new FakeProfanityCheckClient());
        ProductPrice productPrice = new ProductPrice(BigDecimal.valueOf(price));
        return new Product(id, productName, productPrice);
    }
}
