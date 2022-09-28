package kitchenpos.tobe;

import java.math.BigDecimal;
import java.util.UUID;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductPrice;

public class Fixtures {

    public static Product product() {
        return product("후라이드", 16_000L);
    }

    public static Product product(String name, long price) {
        return product(UUID.randomUUID(), name, price);
    }

    public static Product product(UUID id, String name, long price) {
        ProductPrice productPrice = new ProductPrice(BigDecimal.valueOf(price));
        return new Product(id, name, productPrice);
    }
}
