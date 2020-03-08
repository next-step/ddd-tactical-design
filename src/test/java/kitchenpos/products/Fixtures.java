package kitchenpos.products;

import kitchenpos.products.model.ProductData;

import java.math.BigDecimal;

public class Fixtures {
    public static final Long FRIED_CHICKEN_ID = 1L;
    public static final Long SEASONED_CHICKEN_ID = 2L;

    public static ProductData friedChicken() {
        final ProductData productData = new ProductData();
        productData.setId(FRIED_CHICKEN_ID);
        productData.setName("후라이드");
        productData.setPrice(BigDecimal.valueOf(16_000L));
        return productData;
    }

    public static ProductData seasonedChicken() {
        final ProductData productData = new ProductData();
        productData.setId(SEASONED_CHICKEN_ID);
        productData.setName("양념치킨");
        productData.setPrice(BigDecimal.valueOf(16_000L));
        return productData;
    }
}
