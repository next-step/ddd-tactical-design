package kitchenpos.reader.domain;

import java.math.BigDecimal;
import java.util.UUID;

public class MenuPriceAndDisplayed {

    private final UUID menuId;
    private final BigDecimal price;
    private final boolean displayed;

    public MenuPriceAndDisplayed(UUID menuId, BigDecimal price, boolean displayed) {
        this.menuId = menuId;
        this.price = price;
        this.displayed = displayed;
    }

    public UUID getMenuId() {
        return menuId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public boolean isNotDisplayed() {
        return !isDisplayed();
    }
}
