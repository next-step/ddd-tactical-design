package kitchenpos.menus.application.dto;

import java.math.BigDecimal;

public class MenuChangePriceRequest {
    private BigDecimal price;

    public MenuChangePriceRequest() {
    }

    public MenuChangePriceRequest(BigDecimal price) {
        this.price = price;
    }

    public MenuChangePriceRequest(long price) {
        this.price = BigDecimal.valueOf(price);
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
