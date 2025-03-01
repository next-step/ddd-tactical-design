package kitchenpos.eatinorders.tobe.domain.order;

import kitchenpos.eatinorders.tobe.domain.order.vo.*;

import java.util.UUID;

import static java.util.Objects.*;

public class EatInOrderLineItem {
    private final EatInOrderLineItemId eatInOrderLineItemId;
    private final UUID menuId;
    private final EatInOrderLineItemName name;
    private final EatInOrderLineItemPrice price;
    private final Quantity quantity;
    private EatInOrderId eatInOrderId;

    public EatInOrderLineItem(final UUID menuId, final String name, final int price, final int quantity) {
        this(new EatInOrderLineItemId(), menuId, new EatInOrderLineItemName(name), new EatInOrderLineItemPrice(price), new Quantity(quantity));
    }

    public EatInOrderLineItem(
            final EatInOrderLineItemId eatInOrderLineItemId,
            final UUID menuId,
            final EatInOrderLineItemName name,
            final EatInOrderLineItemPrice price,
            final Quantity quantity
    ) {
        verify(eatInOrderLineItemId, menuId, name, price, quantity);
        this.eatInOrderLineItemId = eatInOrderLineItemId;
        this.menuId = menuId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    private void verify(final EatInOrderLineItemId eatInOrderLineItemId, final UUID menuId,
                        final EatInOrderLineItemName name, final EatInOrderLineItemPrice price, final Quantity quantity) {
        if (isNull(eatInOrderLineItemId) || isNull(menuId) || isNull(name) || isNull(price) || isNull(quantity)) {
            throw new IllegalArgumentException();
        }
    }

    public UUID eatInOrderLineItemIdValue() {
        return eatInOrderLineItemId.getValue();
    }

    public UUID menuId() {
        return menuId;
    }

    public String orderLineItemNameValue() {
        return name.getValue();
    }

    public int orderLineItemPriceValue() {
        return price.getValue();
    }

    public int quantityValue() {
        return quantity.getValue();
    }

    public UUID eatInOrderIdValue() {
        return eatInOrderId.getValue();
    }

    public void setEatInOrderId(final EatInOrderId eatInOrderId) {
        this.eatInOrderId = eatInOrderId;
    }
}
