package kitchenpos.products;

import kitchenpos.products.tobe.domain.product.domain.Product;

import java.math.BigDecimal;

public class Fixtures {
    public static final Long FRIED_CHICKEN_ID = 1L;
    public static final Long SEASONED_CHICKEN_ID = 2L;

    public static Product friedChicken(BigDecimal price) {
        final Product product = new Product(FRIED_CHICKEN_ID, "후라이드", price);
        return product;
    }

    public static Product friedChicken() {
        final Product product = new Product(FRIED_CHICKEN_ID, "후라이드", BigDecimal.valueOf(16_000L));
        return product;
    }

    public static Product seasonedChicken() {
        final Product product = new Product(SEASONED_CHICKEN_ID, "양념치킨", BigDecimal.valueOf(16_000L));
        return product;
    }
}
