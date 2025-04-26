package kitchenpos.eatinorders.tobe.domain.order;

import java.util.UUID;

public class DefaultEatInOrderMenu implements EatInOrderMenu {
    private final UUID menuId;
    private final int price;
    private final boolean displayed;

    public DefaultEatInOrderMenu(final UUID menuId, final int price, final boolean displayed) {
        this.menuId = menuId;
        this.price = price;
        this.displayed = displayed;
    }

    @Override
    public UUID getMenuId() {
        return menuId;
    }

    @Override
    public boolean isDisplayed() {
        return displayed;
    }

    @Override
    public boolean isSamePrice(int price) {
        return this.price == price;
    }
}
