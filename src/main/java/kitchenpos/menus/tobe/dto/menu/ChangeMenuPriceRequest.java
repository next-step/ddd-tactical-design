package kitchenpos.menus.tobe.dto.menu;

import java.math.BigDecimal;

public class ChangeMenuPriceRequest {

    private BigDecimal price;

    public ChangeMenuPriceRequest(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal price() {
        return price;
    }
}
