package kitchenpos.eatinorders.tobe.domain.order;

import kitchenpos.eatinorders.tobe.domain.order.vo.EatInOrderId;
import kitchenpos.eatinorders.tobe.domain.order.vo.EatInOrderLineItemId;
import kitchenpos.eatinorders.tobe.domain.order.vo.Quantity;

import java.util.UUID;

public class EatInOrderLineItem {

    private final EatInOrderLineItemId id;
    private final EatInOrderLineItemMenu eatInOrderLineItemMenu;
    private final Quantity quantity;
    private EatInOrderId eatInOrderId;

    public EatInOrderLineItem(final EatInOrderLineItemMenu eatInOrderLineItemMenu, final int quantity) {
        this(new EatInOrderLineItemId(), eatInOrderLineItemMenu, new Quantity(quantity));
    }

    public EatInOrderLineItem(final EatInOrderLineItemId id, final EatInOrderLineItemMenu eatInOrderLineItemMenu, final int quantity) {
        this(id, eatInOrderLineItemMenu, new Quantity(quantity));
    }

    public EatInOrderLineItem(
            final EatInOrderLineItemId id,
            final EatInOrderLineItemMenu eatInOrderLineItemMenu,
            final Quantity quantity
    ) {
        this.id = id;
        this.eatInOrderLineItemMenu = eatInOrderLineItemMenu;
        this.quantity = quantity;
        this.eatInOrderLineItemMenu.setEatInOrderLineItemSeq(id);
    }

    public boolean isSameMenu(final UUID menuId) {
        return eatInOrderLineItemMenu.isSameMenu(menuId);
    }

    public boolean isSamePrice(final int menuPrice) {
        return eatInOrderLineItemMenu.isSamePrice(menuPrice);
    }

    public UUID menuId() {
        return eatInOrderLineItemMenu.menuId();
    }

    public int menuPrice() {
        return eatInOrderLineItemMenu.priceValue();
    }

    public EatInOrderLineItemMenu eatInOrderLineItemMenu() {
        return eatInOrderLineItemMenu;
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
}
