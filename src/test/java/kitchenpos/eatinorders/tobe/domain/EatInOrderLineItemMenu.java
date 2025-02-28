package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.eatinorders.tobe.domain.vo.EatInOrderLineItemMenuPrice;

import java.util.UUID;

public class EatInOrderLineItemMenu {
    private final UUID menuId;
    private final String name;
    private final EatInOrderLineItemMenuPrice price;

    public EatInOrderLineItemMenu(final UUID menuId, final String name, final int price) {
        this(menuId, name, new EatInOrderLineItemMenuPrice(price));
    }

    public EatInOrderLineItemMenu(final UUID menuId, final String name, final EatInOrderLineItemMenuPrice price) {
        this.menuId = menuId;
        this.name = name;
        this.price = price;
    }

    public boolean isSameMenu(final UUID menuId) {
        return this.menuId.equals(menuId);
    }

    public boolean isSamePrice(final int price) {
        return this.price.isSamePrice(price);
    }
}
