package kitchenpos.menus.application.dto;

import java.math.BigDecimal;

public class MenuPriceChangeRequest {
    private final BigDecimal price;

    public MenuPriceChangeRequest(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
