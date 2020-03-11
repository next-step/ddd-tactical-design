package kitchenpos.products;

import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.dto.ProductRequestDto;

import java.math.BigDecimal;

public class Fixtures {

    public static Product friedChicken() {
        final ProductRequestDto productRequestDto = new ProductRequestDto();
        productRequestDto.setName("후라이드");
        productRequestDto.setPrice(BigDecimal.valueOf(16_000L));
        return productRequestDto.toEntity();
    }

    public static Product seasonedChicken() {
        final ProductRequestDto productRequestDto = new ProductRequestDto();
        productRequestDto.setName("양념치킨");
        productRequestDto.setPrice(BigDecimal.valueOf(16_000L));
        return productRequestDto.toEntity();
    }

    public static ProductRequestDto friedChickenRequest() {
        final ProductRequestDto productRequestDto = new ProductRequestDto();
        productRequestDto.setName("후라이드");
        productRequestDto.setPrice(BigDecimal.valueOf(16_000L));
        return productRequestDto;
    }
}
