package kitchenpos.menus.tobe.dto;

import java.math.BigDecimal;

public class MenuChangePriceDto {
    private BigDecimal price;

    public MenuChangePriceDto(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
