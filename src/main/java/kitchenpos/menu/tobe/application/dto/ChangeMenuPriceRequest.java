package kitchenpos.menu.tobe.application.dto;

import java.math.BigDecimal;

public class ChangeMenuPriceRequest {

    private final BigDecimal price;

    public ChangeMenuPriceRequest(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
