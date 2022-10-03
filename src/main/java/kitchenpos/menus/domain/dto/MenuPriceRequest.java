package kitchenpos.menus.domain.dto;

import java.math.BigDecimal;

public class MenuPriceRequest {
    private BigDecimal price;

    protected MenuPriceRequest() {
    }

    public MenuPriceRequest(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
