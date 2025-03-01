package kitchenpos.eatinorders.tobe.domain.order;

import kitchenpos.eatinorders.tobe.domain.order.vo.EatInOrderId;
import kitchenpos.eatinorders.tobe.domain.ordertable.OrderTable;
import kitchenpos.eatinorders.tobe.domain.ordertable.vo.OrderTableId;

import java.util.Optional;

import static java.util.Objects.isNull;

public class EatInOrder {
    private final EatInOrderId id;
    private EatInOrderStatus eatInOrderStatus;
    private final EatInOrderDateTime eatInOrderDateTime;
    private final EatInOrderLineItems eatInOrderLineItems;
    private final OrderTableId orderTableId;

    public EatInOrder(final EatInOrderLineItems eatInOrderLineItems, final EatInOrderMenus eatInOrderMenus, final OrderTable orderTable) {
        this(new EatInOrderId(), EatInOrderStatus.WAITING, new EatInOrderDateTime(), eatInOrderLineItems, eatInOrderMenus, orderTable);
    }

    public EatInOrder(final EatInOrderId id, final EatInOrderStatus eatInOrderStatus,
                      final EatInOrderDateTime eatInOrderDateTime, final EatInOrderLineItems eatInOrderLineItems,
                      final EatInOrderMenus eatInOrderMenus, final OrderTable orderTable) {
        verify(id, eatInOrderStatus, eatInOrderDateTime, eatInOrderLineItems, eatInOrderMenus, orderTable);
        this.id = id;
        this.eatInOrderStatus = eatInOrderStatus;
        this.eatInOrderDateTime = eatInOrderDateTime;
        this.eatInOrderLineItems = eatInOrderLineItems;
        this.orderTableId = Optional.ofNullable(orderTable)
                .map(OrderTable::id)
                .orElseThrow(IllegalArgumentException::new);
    }

    private void verify(final EatInOrderId id, final EatInOrderStatus eatInOrderStatus,
                        final EatInOrderDateTime eatInOrderDateTime, final EatInOrderLineItems eatInOrderLineItems,
                        final EatInOrderMenus eatInOrderMenus, final OrderTable orderTable) {
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
        this.eatInOrderStatus = eatInOrderStatus.accepted();
    }

    public void served() {
        this.eatInOrderStatus = eatInOrderStatus.served();
    }

    public void completed() {
        this.eatInOrderStatus = eatInOrderStatus.completed();
    }
}
