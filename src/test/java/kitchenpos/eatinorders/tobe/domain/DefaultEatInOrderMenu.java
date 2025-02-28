package kitchenpos.eatinorders.tobe.domain;

import java.util.UUID;

public class DefaultEatInOrderMenu implements EatInOrderMenu {

    private final UUID menuId;
    private final String name;
    private final int price;
    private final boolean displayed;

    public DefaultEatInOrderMenu(final UUID menuId, final String name, final int price,  final boolean displayed) {
        this.menuId = menuId;
        this.name = name;
        this.price = price;
        this.displayed = displayed;
    }

    public boolean isSameMenu(final UUID uuid) {
        return menuId.equals(uuid);
    }

    public boolean isSamePrice(final int price) {
        return this.price == price;
    }

    public boolean isDisplayed() {
        return displayed;
    }
}
