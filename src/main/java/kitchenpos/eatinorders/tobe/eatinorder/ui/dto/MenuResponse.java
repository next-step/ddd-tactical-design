package kitchenpos.eatinorders.tobe.eatinorder.ui.dto;

import kitchenpos.menus.tobe.menu.domain.MenuId;

import java.math.BigDecimal;
import java.util.UUID;

public class MenuResponse {
    private final MenuId menuId;
    private final BigDecimal price;
    private final boolean displayed;

    public MenuResponse(final UUID menuId, final BigDecimal price, final boolean displayed) {
        this(new MenuId(menuId), price, displayed);
    }

    private MenuResponse(final MenuId menuId, final BigDecimal price, final boolean displayed) {
        this.menuId = menuId;
        this.price = price;
        this.displayed = displayed;
    }

    public UUID getMenuId() {
        return menuId.getId();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public boolean isDisplayed() {
        return displayed;
    }
}
