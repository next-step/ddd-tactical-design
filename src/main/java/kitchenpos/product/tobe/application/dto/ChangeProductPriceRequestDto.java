package kitchenpos.product.tobe.application.dto;

import java.math.BigDecimal;

public class ChangeProductPriceRequestDto {
    private final BigDecimal price;

    public ChangeProductPriceRequestDto(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
