package kitchenpos.eatinorders.tobe.poc;

import jakarta.annotation.Nullable;
import kitchenpos.eatinorders.tobe.domain.order.vo.EatInOrderLineItemPrice;
import kitchenpos.eatinorders.tobe.domain.order.vo.EatInOrderLineItemId;

import java.util.UUID;

@Deprecated
public class EatInOrderLineItemMenu {
    private final UUID menuId;
    private final String name;
    private final EatInOrderLineItemPrice price;
    private EatInOrderLineItemId eatInOrderLineItemId;

    public EatInOrderLineItemMenu(final UUID menuId, final String name, final int price) {
        this(menuId, name, new EatInOrderLineItemPrice(price));
    }

    public EatInOrderLineItemMenu(final UUID menuId, final String name, final EatInOrderLineItemPrice price) {
        this.menuId = menuId;
        this.name = name;
        this.price = price;
        throw new UnsupportedOperationException("EatInOrderLineItemMenu is deprecated");
    }

    public UUID menuId() {
        return menuId;
    }

    public String name() {
        return name;
    }

    public int priceValue() {
        return price.getValue();
    }

    public UUID eatInOrderLineItemIdValue() {
        return eatInOrderLineItemId.getValue();
    }

    public void setEatInOrderLineItemSeq(@Nullable final EatInOrderLineItemId seq) {
        this.eatInOrderLineItemId = seq;
    }
}
