package kitchenpos.eatinorder.domain.model.todo;

import java.util.Objects;
import java.util.UUID;

public class EatInOrderLineItem {
    private final Long seq;
    private final UUID menuId;
    private final EatInOrderLineItemQuantity quantity;
    private final EatInOrderLineItemPrice price;

    private EatInOrderLineItem(
            final Long seq,
            final UUID menuId,
            final EatInOrderLineItemQuantity quantity,
            final EatInOrderLineItemPrice price
    ) {
        this.seq = seq;
        this.menuId = menuId;
        this.quantity = quantity;
        this.price = price;
    }

    public static EatInOrderLineItem of(
            final Long seq,
            final UUID menuId,
            final long quantity,
            final long menuPrice,
            final boolean isDisplayedMenu
    ) {
        if (!isDisplayedMenu) {
            throw new IllegalStateException("주문할 수 없는 메뉴입니다. menuId=" + menuId);
        }
        return new EatInOrderLineItem(seq, menuId, EatInOrderLineItemQuantity.of(quantity), EatInOrderLineItemPrice.of(menuPrice));
    }

    public static EatInOrderLineItem of(
            final Long seq,
            final UUID menuId,
            final long quantity,
            final long menuPrice
    ) {
        return new EatInOrderLineItem(seq, menuId, EatInOrderLineItemQuantity.of(quantity), EatInOrderLineItemPrice.of(menuPrice));
    }

    public Long getSeq() {
        return seq;
    }

    public UUID getMenuId() {
        return menuId;
    }

    public long getQuantity() {
        return quantity.value();
    }

    public long getPrice() {
        return price.value();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        EatInOrderLineItem that = (EatInOrderLineItem) o;
        return Objects.equals(seq, that.seq);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(seq);
    }
}
