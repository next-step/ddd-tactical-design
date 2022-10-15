package kitchenpos.menus.tobe.dto.menu;

import java.math.BigDecimal;

public class ChangePriceRequest {

    private BigDecimal price;

    public ChangePriceRequest(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal price() {
        return price;
    }
}
