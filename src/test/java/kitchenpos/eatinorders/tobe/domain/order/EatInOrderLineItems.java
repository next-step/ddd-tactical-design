package kitchenpos.eatinorders.tobe.domain.order;

import kitchenpos.eatinorders.tobe.domain.order.menu.EatInOrderMenus;
import kitchenpos.eatinorders.tobe.domain.order.vo.EatInOrderId;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.*;

public class EatInOrderLineItems {
    private final List<EatInOrderLineItem> eatInOrderLineItems;

    public EatInOrderLineItems(final EatInOrderMenus eatInOrderMenus, final EatInOrderLineItem... eatInOrderLineItem) {
        this(eatInOrderMenus, List.of(eatInOrderLineItem));
    }

    public EatInOrderLineItems(final EatInOrderMenus eatInOrderMenus, final List<EatInOrderLineItem> eatInOrderLineItems) {
        verify(eatInOrderMenus, eatInOrderLineItems);
        this.eatInOrderLineItems = eatInOrderLineItems;
    }

    private void verify(final EatInOrderMenus eatInOrderMenus, final List<EatInOrderLineItem> eatInOrderLineItems) {
        if (isNull(eatInOrderMenus) || isNull(eatInOrderLineItems) || eatInOrderLineItems.isEmpty()) {
            throw new IllegalArgumentException();
        }
        if(!eatInOrderMenus.isSameSize(eatInOrderLineItems.size())) {
            throw new IllegalArgumentException();
        }
        eatInOrderLineItems.forEach(eatInOrderLineItem -> verify(eatInOrderMenus, eatInOrderLineItem));
    }

    private void verify(final EatInOrderMenus eatInOrderMenus, final EatInOrderLineItem eatInOrderLineItem) {
        if(!eatInOrderMenus.isDisplayed(eatInOrderLineItem.menuId())) {
            throw new IllegalArgumentException();
        }
        if(!eatInOrderMenus.isSamePrice(eatInOrderLineItem.menuId(), eatInOrderLineItem.orderLineItemPriceValue())) {
            throw new IllegalArgumentException();
        }
    }

    public int size() {
        return eatInOrderLineItems.size();
    }

    public List<EatInOrderLineItem> eatInOrderLineItems() {
        return new ArrayList<>(eatInOrderLineItems);
    }

    public void setEatInOrderId(final EatInOrderId eatInOrderId) {
        eatInOrderLineItems.forEach(eatInOrderLineItem -> eatInOrderLineItem.setEatInOrderId(eatInOrderId));
    }
}
