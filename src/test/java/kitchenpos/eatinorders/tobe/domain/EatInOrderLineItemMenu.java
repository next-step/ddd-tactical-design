package kitchenpos.eatinorders.tobe.domain;

import java.util.UUID;

public class EatInOrderLineItemMenu {
    private final UUID menuId;
    private final String name;
    private final int price;

    public EatInOrderLineItemMenu(final UUID menuId, final String name, final int price) {
        this.menuId = menuId;
        this.name = name;
        this.price = price;
    }
}
