package kitchenpos.menus.ui.request;

import java.math.BigDecimal;

public class MenuPriceChangeRequest {

    private BigDecimal price;

    protected MenuPriceChangeRequest() {
    }

    public MenuPriceChangeRequest(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
