package kitchenpos.products.dto;

import java.math.BigDecimal;

public class ProductDtoBuilder {
    public static ProductCreateDto productCreateDtoBuild(final String name, final BigDecimal price) {
        return new ProductCreateDto(name, price);
    }

    public static PriceUpdateDto priceUpdateDtoBuild(final BigDecimal price) {
        return new PriceUpdateDto(price);
    }
}
