package kitchenpos.products.tobe;

import kitchenpos.products.tobe.domain.Product;
import org.springframework.test.util.ReflectionTestUtils;

public class Fixtures {

    public static final Long FRIED_CHICKEN_ID = 1L;
    public static final Long SEASONED_CHICKEN_ID = 2L;

    public static Product friedChicken() {
        final Product product = new Product("후라이드", 15000L);
        ReflectionTestUtils.setField(product, "id", FRIED_CHICKEN_ID);
        return product;
    }

    public static Product seasonedChicken() {
        final Product product = new Product("양념치킨", 16000L);
        ReflectionTestUtils.setField(product, "id", SEASONED_CHICKEN_ID);
        return product;
    }
}
