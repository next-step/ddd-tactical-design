package kitchenpos.eatinorders.tobe.domain.order;

import java.util.Objects;
import java.util.UUID;

public class EatInOrder {
    private final UUID id = UUID.randomUUID();
    private final EatInOrderLineItems eatInOrderLineItems;
    private final UUID orderTableId;

    public EatInOrder(final EatInOrderLineItems eatInOrderLineItems, final EatInOrderMenus eatInOrderMenus, final EatInOrderTable orderTable) {
        verify(eatInOrderLineItems, eatInOrderMenus, orderTable);
        this.eatInOrderLineItems = eatInOrderLineItems;
        this.orderTableId = orderTable.id();
    }

    private static void verify(final EatInOrderLineItems eatInOrderLineItems, final EatInOrderMenus eatInOrderMenus, final EatInOrderTable orderTable) {
        if(Objects.isNull(orderTable) || Objects.isNull(eatInOrderLineItems) || Objects.isNull(eatInOrderMenus)){
            throw new IllegalArgumentException();
        }
        eatInOrderLineItems.verify(eatInOrderMenus);
        if (!orderTable.isOccupiedValue()) {
            throw new IllegalArgumentException();
        }
    }

    public int size() {
        return eatInOrderLineItems.size();
    }

    public UUID getId() {
        return id;
    }
}
