package kitchenpos.eatinorders.tobe.domain.order;

import kitchenpos.eatinorders.tobe.domain.order.vo.EatInOrderId;

import java.util.Optional;
import java.util.UUID;

import static java.util.Objects.isNull;

public class EatInOrder {
    private final EatInOrderId id;
    private final EatInOrderStatus eatInOrderStatus;
    private final EatInOrderDateTime eatInOrderDateTime;
    private final EatInOrderLineItems eatInOrderLineItems;
    private final UUID orderTableId;

    public EatInOrder(final EatInOrderLineItems eatInOrderLineItems, final EatInOrderMenus eatInOrderMenus, final EatInOrderTable orderTable) {
        this(new EatInOrderId(), eatInOrderLineItems, EatInOrderStatus.WAITING, new EatInOrderDateTime(), eatInOrderMenus, orderTable);
    }

    public EatInOrder(final EatInOrderId id, final EatInOrderLineItems eatInOrderLineItems, final EatInOrderStatus eatInOrderStatus,
                      final EatInOrderDateTime eatInOrderDateTime, final EatInOrderMenus eatInOrderMenus, final EatInOrderTable orderTable) {
        verify(id, eatInOrderLineItems, eatInOrderStatus, eatInOrderDateTime, eatInOrderMenus, orderTable);
        this.id = id;
        this.eatInOrderStatus = eatInOrderStatus;
        this.eatInOrderDateTime = eatInOrderDateTime;
        this.eatInOrderLineItems = eatInOrderLineItems;
        this.orderTableId = Optional.ofNullable(orderTable)
                .map(EatInOrderTable::id)
                .orElseThrow(IllegalArgumentException::new);
    }

    private void verify(final EatInOrderId id, final EatInOrderLineItems eatInOrderLineItems,
                        final EatInOrderStatus eatInOrderStatus, final EatInOrderDateTime eatInOrderDateTime,
                        final EatInOrderMenus eatInOrderMenus, final EatInOrderTable orderTable) {
        if (isNull(id) || isNull(orderTable) || isNull(eatInOrderStatus) ||
                isNull(eatInOrderDateTime) || isNull(eatInOrderLineItems) || isNull(eatInOrderMenus)) {
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

    public EatInOrderId getId() {
        return id;
    }
}
