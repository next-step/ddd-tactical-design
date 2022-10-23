package kitchenpos.menus.dto;

import java.math.BigDecimal;

public class MenuPriceChangeRequest {
    private BigDecimal price;

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
