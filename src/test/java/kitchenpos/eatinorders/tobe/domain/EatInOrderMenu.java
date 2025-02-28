package kitchenpos.eatinorders.tobe.domain;

import java.util.UUID;

public class EatInOrderMenu {

    private final UUID menuId;
    private final int price;
    private final String name;
    private final boolean displayed;

    public EatInOrderMenu(final UUID menuId, final int price, final String name, final boolean displayed) {
        this.menuId = menuId;
        this.price = price;
        this.name = name;
        this.displayed = displayed;
    }
}
