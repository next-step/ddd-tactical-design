package kitchenpos.eatinorders.tobe.domain.order;

import kitchenpos.eatinorders.tobe.domain.order.vo.EatInOrderId;
import kitchenpos.eatinorders.tobe.domain.order.vo.EatInOrderLineItemId;
import kitchenpos.eatinorders.tobe.domain.order.vo.EatInOrderLineItemPrice;
import kitchenpos.eatinorders.tobe.domain.order.vo.Quantity;

import java.util.UUID;

public class EatInOrderLineItem {

    private final EatInOrderLineItemId id;
    private final UUID menuId;
    private final String name;
    private final EatInOrderLineItemPrice price;
    private final Quantity quantity;
    private EatInOrderId eatInOrderId;

    public EatInOrderLineItem(final UUID menuId, final String name, final int price, final int quantity) {
        this(new EatInOrderLineItemId(), menuId, name, new EatInOrderLineItemPrice(price), new Quantity(quantity));
    }

    public EatInOrderLineItem(
            final EatInOrderLineItemId id,
            final UUID menuId,
            final String name,
            final EatInOrderLineItemPrice price,
            final Quantity quantity
    ) {
        this.id = id;
        this.menuId = menuId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public boolean isSameMenu(final UUID menuId) {
        return this.menuId.equals(menuId);
    }

    public boolean isSamePrice(final int menuPrice) {
        return price.isSamePrice(menuPrice);
    }

    public UUID menuId() {
        return menuId;
    }

    public int orderLineItemPrice() {
        return price.value();
    }

    public UUID eatInOrderIdValue() {
        return eatInOrderId.getValue();
    }

    public void setEatInOrderId(final EatInOrderId eatInOrderId) {
        this.eatInOrderId = eatInOrderId;
    }

    public long quantityValue() {
        return quantity.getValue();
    }

    public UUID idValue() {
        return id.getValue();
    }

    public String name() {
        return name;
    }

    public int priceValue() {
        return price.value();
    }

    public EatInOrderId eatInOrderId() {
        return eatInOrderId;
    }
}
