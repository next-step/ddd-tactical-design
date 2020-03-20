package kitchenpos.products.tobe;

import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductBuilder;
import kitchenpos.products.tobe.domain.ProductPrice;

import java.math.BigDecimal;

public class Fixtures {
    public static final Long FRIED_CHICKEN_ID = 1L;
    public static final Long SEASONED_CHICKEN_ID = 2L;

    public static Product friedChicken() {
        final Product product = ProductBuilder.aProduct()
                                .withId(FRIED_CHICKEN_ID)
                                .withName("후라이드")
                                .withPrice(BigDecimal.valueOf(16_000L))
                                .build();

        return product;
    }

    public static Product seasonedChicken() {
        final Product product = ProductBuilder.aProduct()
                .withId(SEASONED_CHICKEN_ID)
                .withName("양념치킨")
                .withPrice(BigDecimal.valueOf(16_000L))
                .build();

        return product;
    }
}
