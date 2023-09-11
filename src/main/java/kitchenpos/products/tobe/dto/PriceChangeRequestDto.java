package kitchenpos.products.tobe.dto;

import java.math.BigDecimal;

public class PriceChangeRequestDto {
    private Long price;

    public PriceChangeRequestDto(Long price) {
        this.price = price;
    }

    public Long getPrice() {
        return price;
    }
}
