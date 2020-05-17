package kitchenpos.products;

import java.math.BigDecimal;
import kitchenpos.products.model.ProductRequest;
import kitchenpos.products.tobe.domain.Product;

public class Fixtures {

    public static final Long FRIED_CHICKEN_ID = 1L;
    public static final Long SEASONED_CHICKEN_ID = 2L;

    public static ProductRequest friedChickenRequest() {
        final ProductRequest product = new ProductRequest();
        product.setName("후라이드");
        product.setPrice(BigDecimal.valueOf(16_000L));
        return product;
    }

    public static Product friedChicken() {
        return new Product(FRIED_CHICKEN_ID, "후라이드", BigDecimal.valueOf(16_000L));
    }

    public static ProductRequest seasonedChickenRequest() {
        final ProductRequest product = new ProductRequest();
        product.setName("양념치킨");
        product.setPrice(BigDecimal.valueOf(16_000L));
        return product;
    }

    public static Product seasonedChicken() {
        return new Product(SEASONED_CHICKEN_ID, "양념치킨", BigDecimal.valueOf(16_000L));
    }

}
