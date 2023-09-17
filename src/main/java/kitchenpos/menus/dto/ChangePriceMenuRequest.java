package kitchenpos.menus.dto;

import java.math.BigDecimal;

public class ChangePriceMenuRequest {

    private final BigDecimal price;

    public ChangePriceMenuRequest(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }

}
