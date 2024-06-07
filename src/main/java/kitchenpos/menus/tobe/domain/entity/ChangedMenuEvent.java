package kitchenpos.menus.tobe.domain.entity;

import java.math.BigDecimal;
import java.util.UUID;

public class ChangedMenuEvent {
    private final UUID menuId;
    private final BigDecimal price;
    private final boolean isDisplayed;

    public ChangedMenuEvent(UUID menuId, BigDecimal price, boolean isDisplayed) {
        this.menuId = menuId;
        this.price = price;
        this.isDisplayed = isDisplayed;
    }

    public UUID getMenuId() {
        return menuId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public boolean isDisplayed() {
        return isDisplayed;
    }
}
