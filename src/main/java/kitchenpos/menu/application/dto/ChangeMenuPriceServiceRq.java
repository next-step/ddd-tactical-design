package kitchenpos.menu.application.dto;

import java.math.BigDecimal;

public class ChangeMenuPriceServiceRq {
    private BigDecimal price;

    public ChangeMenuPriceServiceRq(BigDecimal price) {
        this.price = price;
    }

    public ChangeMenuPriceServiceRq() {
    }

    public BigDecimal getPrice() {
        return price;
    }
}
