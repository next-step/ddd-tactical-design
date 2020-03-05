package kitchenpos.products;

import kitchenpos.products.tobe.domain.Product;

import java.math.BigDecimal;

public class Fixtures {
    public static final Long FRIED_CHICKEN_ID = 1L;
    public static final Long SEASONED_CHICKEN_ID = 2L;

    public static Product friedChicken() {
        return Product.from(
                FRIED_CHICKEN_ID,
                "후라이드",
                BigDecimal.valueOf(16_000L));
    }

    public static Product seasonedChicken() {
        return Product.from(
                SEASONED_CHICKEN_ID,
                "양념치킨",
                BigDecimal.valueOf(16_000L));
    }
}
