package kitchenpos.products;

import kitchenpos.products.tobe.domain.Product;

import java.math.BigDecimal;

public class Fixtures {
    public static final Long FRIED_CHICKEN_ID = 1L;
    public static final Long SEASONED_CHICKEN_ID = 2L;

    public static Product friedChicken() {
        final Product product = new Product();
        product.setId(FRIED_CHICKEN_ID);
        product.setName("후라이드");
        product.setPrice(BigDecimal.valueOf(16_000L));
        return product;
    }

    public static Product seasonedChicken() {
        final Product product = new Product();
        product.setId(SEASONED_CHICKEN_ID);
        product.setName("양념치킨");
        product.setPrice(BigDecimal.valueOf(16_000L));
        return product;
    }
}
