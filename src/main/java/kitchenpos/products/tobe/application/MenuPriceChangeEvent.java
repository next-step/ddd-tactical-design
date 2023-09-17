package kitchenpos.products.tobe.application;

import java.math.BigDecimal;
import java.util.UUID;

public class MenuPriceChangeEvent {
    private final UUID menuId;
    private final BigDecimal newPrice;

    public MenuPriceChangeEvent(final UUID menuId, final BigDecimal newPrice) {
        this.menuId = menuId;
        this.newPrice = newPrice;
    }

    public UUID getMenuId() {
        return menuId;
    }

    public BigDecimal getNewPrice() {
        return newPrice;
    }
}
