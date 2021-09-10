package kitchenpos;

import java.math.BigDecimal;
import java.util.UUID;
import kitchenpos.products.tobe.domain.DisplayedName;
import kitchenpos.products.tobe.domain.FakeProfanities;
import kitchenpos.products.tobe.domain.Price;
import kitchenpos.products.tobe.domain.Product;

public class ToBeFixtures {

    public static final UUID INVALID_ID = new UUID(0L, 0L);

    public static Product product() {
        return product("후라이드", 16_000L);
    }

    public static Product product(final String displayedName, final long price) {
        return product(UUID.randomUUID(), displayedName, price);
    }

    public static Product product(final UUID id, final String displayedName, final long price) {
        return new Product(
            id,
            new DisplayedName(displayedName, new FakeProfanities()),
            new Price(BigDecimal.valueOf(price))
        );
    }
}
