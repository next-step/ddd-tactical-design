package kitchenpos.eatinorders.tobe.domain.order;

import kitchenpos.eatinorders.tobe.domain.order.vo.EatInOrderId;

import java.util.Optional;
import java.util.UUID;

import static java.util.Objects.isNull;

public class EatInOrder {
    private final EatInOrderId id;
    private EatInOrderStatus eatInOrderStatus;
    private final EatInOrderDateTime eatInOrderDateTime;
    private final EatInOrderLineItems eatInOrderLineItems;
    private final UUID orderTableId;

    public EatInOrder(final EatInOrderLineItems eatInOrderLineItems, final EatInOrderMenus eatInOrderMenus, final EatInOrderTable orderTable) {
        this(new EatInOrderId(), EatInOrderStatus.WAITING, new EatInOrderDateTime(), eatInOrderLineItems, eatInOrderMenus, orderTable);
    }

    public EatInOrder(final EatInOrderId id, final EatInOrderStatus eatInOrderStatus,
                      final EatInOrderDateTime eatInOrderDateTime, final EatInOrderLineItems eatInOrderLineItems,
                      final EatInOrderMenus eatInOrderMenus, final EatInOrderTable orderTable) {
        verify(id, eatInOrderStatus, eatInOrderDateTime, eatInOrderLineItems, eatInOrderMenus, orderTable);
        this.id = id;
        this.eatInOrderStatus = eatInOrderStatus;
        this.eatInOrderDateTime = eatInOrderDateTime;
        this.eatInOrderLineItems = eatInOrderLineItems;
        this.orderTableId = Optional.ofNullable(orderTable)
                .map(EatInOrderTable::id)
                .orElseThrow(IllegalArgumentException::new);
    }

    private void verify(final EatInOrderId id, final EatInOrderStatus eatInOrderStatus,
                        final EatInOrderDateTime eatInOrderDateTime, final EatInOrderLineItems eatInOrderLineItems,
                        final EatInOrderMenus eatInOrderMenus, final EatInOrderTable orderTable) {
        if (isNull(id) || isNull(eatInOrderStatus) || isNull(eatInOrderDateTime) ||
                isNull(eatInOrderLineItems) || isNull(eatInOrderMenus) || isNull(orderTable)) {
            throw new IllegalArgumentException();
        }
        eatInOrderLineItems.verify(eatInOrderMenus);
        if (!orderTable.isOccupiedValue()) {
            throw new IllegalArgumentException();
        }
    }

    public EatInOrderId getId() {
        return id;
    }

    public EatInOrderStatus status() {
        return eatInOrderStatus;
    }

    public void accepted() {
        if(EatInOrderStatus.WAITING != this.eatInOrderStatus) {
            throw new IllegalArgumentException();
        }
        this.eatInOrderStatus = EatInOrderStatus.ACCEPTED;
    }
}
