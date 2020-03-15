package kitchenpos.products;

import kitchenpos.products.model.ProductData;
import kitchenpos.products.tobe.application.ProductApplication;
import kitchenpos.products.tobe.domain.Product;

import java.math.BigDecimal;

public class Fixtures {
    public static final Long FRIED_CHICKEN_ID = 1L;
    public static final Long SEASONED_CHICKEN_ID = 2L;

    public static ProductData friedChickenData() {
        return ProductApplication.convertToProductData(friedChicken());
    }

    public static ProductData seasonedChickenData() {
        return ProductApplication.convertToProductData(seasonedChicken());
    }

    public static Product friedChicken() {
        return new Product(FRIED_CHICKEN_ID,"후라이드", BigDecimal.valueOf(16_000L));
    }

    public static Product seasonedChicken() {
        return new Product(SEASONED_CHICKEN_ID,"양념치킨" ,BigDecimal.valueOf(16_000L));
    }

}
