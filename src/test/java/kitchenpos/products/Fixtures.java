package kitchenpos.products;

import kitchenpos.products.dto.ProductRequest;
import kitchenpos.products.dto.ProductResponse;
import kitchenpos.products.tobe.domain.Product;

import java.math.BigDecimal;

public class Fixtures {
    public static final Long FRIED_CHICKEN_ID = 1L;
    public static final Long SEASONED_CHICKEN_ID = 2L;

    public static Product friedChicken() {
        return Product.from(
                "후라이드",
                BigDecimal.valueOf(16_000L));
    }

    public static Product seasonedChicken() {
        return Product.from(
                "양념치킨",
                BigDecimal.valueOf(16_000L));
    }

    public static Product productWithPrice(final BigDecimal price) {
        return Product.from(
                "양념치킨",
                price);
    }

    public static ProductRequest friedChickenRequest() {
        return new ProductRequest(
                "후라이드",
                BigDecimal.valueOf(16_000L));
    }

    public static ProductRequest seasonedChickenRequest() {
        return new ProductRequest(
                "양념치킨",
                BigDecimal.valueOf(16_000L));
    }

    public static ProductRequest productWithPriceRequest(final BigDecimal price) {
        return new ProductRequest(
                "양념치킨",
                price);
    }

    public static ProductResponse friedChickenResponse() {
        return new ProductResponse(
                FRIED_CHICKEN_ID,
                "후라이드",
                BigDecimal.valueOf(16_000L));
    }

    public static ProductResponse seasonedChickenResponse() {
        return new ProductResponse(
                SEASONED_CHICKEN_ID,
                "양념치킨",
                BigDecimal.valueOf(16_000L));
    }
}
