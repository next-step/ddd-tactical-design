package kitchenpos.menus.ui.dto.request;

import java.math.BigDecimal;

public class MenuChangePriceRestRequest {
    final private BigDecimal price;

    public MenuChangePriceRestRequest(BigDecimal price) {
        this.price = price;
    }

    public MenuChangePriceRestRequest(long price) {
        this.price = BigDecimal.valueOf(price);
    }

    public BigDecimal getPrice() {
        return price;
    }

}
