package kitchenpos.menus.application.dto;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class MenuPriceChangeRequest {
    @NotNull
    private final BigDecimal price;


    public MenuPriceChangeRequest(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
