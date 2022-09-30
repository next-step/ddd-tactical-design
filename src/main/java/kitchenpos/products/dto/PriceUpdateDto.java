package kitchenpos.products.dto;

import java.math.BigDecimal;

public class PriceUpdateDto {
    private BigDecimal price;

    PriceUpdateDto(final BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return this.price;
    }
}
