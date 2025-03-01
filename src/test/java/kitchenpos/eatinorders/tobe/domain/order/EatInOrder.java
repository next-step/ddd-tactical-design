package kitchenpos.eatinorders.tobe.domain.order;

import kitchenpos.eatinorders.domain.OrderStatus;
import kitchenpos.eatinorders.tobe.domain.order.vo.EatInOrderId;
import kitchenpos.eatinorders.tobe.domain.ordertable.OrderTable;
import kitchenpos.eatinorders.tobe.domain.ordertable.vo.OrderTableId;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

public class EatInOrder {
    private final EatInOrderId id;
    private EatInOrderStatus eatInOrderStatus;
    private final EatInOrderDateTime eatInOrderDateTime;
    private final EatInOrderLineItems eatInOrderLineItems;
    private final OrderTableId orderTableId;

    public EatInOrder(final EatInOrderLineItems eatInOrderLineItems, final EatInOrderMenus eatInOrderMenus, final OrderTableId orderTableId) {
        this(new EatInOrderId(), EatInOrderStatus.WAITING, new EatInOrderDateTime(), eatInOrderLineItems, eatInOrderMenus, orderTableId);
    }

    public EatInOrder(final EatInOrderId id, final EatInOrderStatus eatInOrderStatus,
                      final EatInOrderDateTime eatInOrderDateTime, final EatInOrderLineItems eatInOrderLineItems,
                      final EatInOrderMenus eatInOrderMenus, final OrderTableId orderTableId) {
        verify(id, eatInOrderStatus, eatInOrderDateTime, eatInOrderLineItems, eatInOrderMenus, orderTableId);
        this.id = id;
        this.eatInOrderStatus = eatInOrderStatus;
        this.eatInOrderDateTime = eatInOrderDateTime;
        this.eatInOrderLineItems = eatInOrderLineItems;
        this.orderTableId = orderTableId;
        eatInOrderLineItems.setEatInOrderMenuId(id);
    }

    private void verify(final EatInOrderId id, final EatInOrderStatus eatInOrderStatus,
                        final EatInOrderDateTime eatInOrderDateTime, final EatInOrderLineItems eatInOrderLineItems,
                        final EatInOrderMenus eatInOrderMenus, final OrderTableId orderTable) {
        if (isNull(id) || isNull(eatInOrderStatus) || isNull(eatInOrderDateTime) ||
                isNull(eatInOrderLineItems) || isNull(eatInOrderMenus) || isNull(orderTable)) {
            throw new IllegalArgumentException();
        }
        eatInOrderLineItems.verify(eatInOrderMenus);
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

    public boolean isSameOrderTable(final OrderTableId orderTableId) {
        return this.orderTableId.equals(orderTableId);
    }

    public boolean isSameStatus(final EatInOrderStatus orderStatus) {
        return this.eatInOrderStatus.isSameStatus(orderStatus);
    }

    public List<EatInOrderLineItem> eatInOrderLineItems() {
        return eatInOrderLineItems.eatInOrderLineItems();
    }
}
