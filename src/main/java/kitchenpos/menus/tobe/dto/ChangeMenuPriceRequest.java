package kitchenpos.menus.tobe.dto;

import java.math.BigDecimal;

public class ChangeMenuPriceRequest {
    private BigDecimal price;

    public ChangeMenuPriceRequest() {}

    public ChangeMenuPriceRequest(final BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
