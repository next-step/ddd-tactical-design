package kitchenpos.menu.ui.dto;

import java.math.BigDecimal;

public class ChangeMenuPriceRq {
    private BigDecimal price;

    public ChangeMenuPriceRq(BigDecimal price) {
        this.price = price;
    }

    public ChangeMenuPriceRq() {
    }

    public BigDecimal getPrice() {
        return price;
    }
}
