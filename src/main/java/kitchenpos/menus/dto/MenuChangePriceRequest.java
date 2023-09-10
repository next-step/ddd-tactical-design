package kitchenpos.menus.dto;

import java.math.BigDecimal;

public class MenuChangePriceRequest {
    private BigDecimal price;

    public MenuChangePriceRequest(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
