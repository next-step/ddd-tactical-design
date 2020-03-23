package kitchenpos.products.tobe;

import kitchenpos.products.tobe.domain.Product;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;

public class ProductFixtures {

    public static final Long PRODUCT_FRIED_CHICKEN_ID = 1L;
    public static final Long PRODUCT_SEASONED_CHICKEN_ID = 2L;

    public static Product productFriedChicken() {
        final Product product = new Product("후라이드", BigDecimal.valueOf(15000));
        ReflectionTestUtils.setField(product, "id", PRODUCT_FRIED_CHICKEN_ID);
        return product;
    }

    public static Product productSeasonedChicken() {
        final Product product = new Product("양념치킨", BigDecimal.valueOf(16000));
        ReflectionTestUtils.setField(product, "id", PRODUCT_SEASONED_CHICKEN_ID);
        return product;
    }
}
