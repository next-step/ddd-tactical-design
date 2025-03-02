package kitchenpos.eatinorder.domain.model.todo;

import java.util.Objects;
import java.util.UUID;

public class EatInOrderLineItem {
    private final EatInOrderLineItemSeq seq;
    private final UUID menuId;
    private final EatInOrderLineItemQuantity quantity;
    private final EatInOrderLineItemPrice price;

    private EatInOrderLineItem(
            final EatInOrderLineItemSeq seq,
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
        return EatInOrderLineItem.of(seq, menuId, quantity, menuPrice, menuPrice, isDisplayedMenu);
    }

    public static EatInOrderLineItem of(
            final Long seq,
            final UUID menuId,
            final long quantity,
            final long menuPrice
    ) {
        return new EatInOrderLineItem(EatInOrderLineItemSeq.of(seq), menuId, EatInOrderLineItemQuantity.of(quantity), EatInOrderLineItemPrice.of(menuPrice));
    }

    public static EatInOrderLineItem of(
            final UUID menuId,
            final long quantity,
            final long itemPrice,
            final long menuPrice,
            final boolean isDisplayedMenu
    ) {
        return EatInOrderLineItem.of(null, menuId, quantity, itemPrice, menuPrice, isDisplayedMenu);
    }

    public static EatInOrderLineItem of(
            final Long seq,
            final UUID menuId,
            final long quantity,
            final long itemPrice,
            final long menuPrice,
            final boolean isDisplayedMenu
    ) {
        if (!isDisplayedMenu) {
            throw new IllegalStateException("주문할 수 없는 메뉴입니다. menuId=" + menuId);
        }
        if (itemPrice != menuPrice) {
            throw new IllegalArgumentException("메뉴의 가격과 주문한 메뉴의 가격이 다릅니다.");
        }
        return new EatInOrderLineItem(EatInOrderLineItemSeq.of(seq), menuId, EatInOrderLineItemQuantity.of(quantity), EatInOrderLineItemPrice.of(menuPrice));
    }

    public Long getSeq() {
        return seq.value();
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
