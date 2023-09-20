package kitchenpos.menus.shared.dto.request;

import java.math.BigDecimal;

public class MenuPriceChangeRequest {
    private BigDecimal price;

    public MenuPriceChangeRequest(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
