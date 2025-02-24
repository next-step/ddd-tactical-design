package kitchenpos.menu.application.service.model;

import java.math.BigDecimal;

public class ChangeMenuPriceRequest {
    private BigDecimal price;

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
