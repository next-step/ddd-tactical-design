package kitchenpos.eatinorders.domain.orders;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class OrderedMenu {
    private final UUID menuId;
    private final BigDecimal price;
    private final boolean displayed;

    public OrderedMenu(UUID menuId, BigDecimal price, boolean displayed) {
        this.menuId = menuId;
        this.price = price;
        this.displayed = displayed;
    }

    public boolean isSameMenuId(UUID menuId) {
        return this.menuId.equals(menuId);
    }

    public boolean isHide() {
        return !this.displayed;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public UUID getMenuId() {
        return menuId;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderedMenu that = (OrderedMenu) o;
        return displayed == that.displayed && Objects.equals(menuId, that.menuId) && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuId, price, displayed);
    }
}
