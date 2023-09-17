package kitchenpos.menus.ui.dto.request;

import java.math.BigDecimal;

public class MenuChangePriceRequest {
    private BigDecimal price;

    public MenuChangePriceRequest() {
    }

    public MenuChangePriceRequest(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
