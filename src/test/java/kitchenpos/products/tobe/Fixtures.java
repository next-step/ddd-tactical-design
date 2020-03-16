package kitchenpos.products.tobe;

import java.math.BigDecimal;
import kitchenpos.products.tobe.domain.entity.Price;
import kitchenpos.products.tobe.domain.entity.Product;

public class Fixtures {

    public static Product friedChicken() {
        return new Product("후라이드", Price.valueOf(BigDecimal.valueOf(16_000L)));
    }

    public static Product seasonedChicken() {
        return new Product("양념치킨", Price.valueOf(BigDecimal.valueOf(17_000L)));
    }
}
