package kitchenpos.menus.ui.request;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class MenuChangePriceRequest {

    @NotNull
    private BigDecimal price;

    public MenuChangePriceRequest() {
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
