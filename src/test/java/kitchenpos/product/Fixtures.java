package kitchenpos.product;

import java.util.UUID;
import kitchenpos.common.name.Name;
import kitchenpos.common.price.Price;
import kitchenpos.product.tobe.domain.entity.Product;

public class Fixtures {

    public static Product product() {
        return product("후라이드", 16_000L);
    }

    public static Product product(final String name, final long price) {
        return new Product(
            UUID.randomUUID(),
            new Name(name),
            new Price(price)
        );
    }
}
