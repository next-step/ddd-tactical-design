package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.common.event.Events;
import kitchenpos.eatinorders.tobe.domain.common.*;
import kitchenpos.eatinorders.tobe.domain.exception.InvalidOrderStatusException;
import kitchenpos.eatinorders.tobe.domain.exception.InvalidOrderTableException;

import java.time.LocalDateTime;

/*
매장 내 식사 주문의
비즈니스 로직을 다루는 클래스
*/

public class EatInOrder {
    private OrderId id;
    private OrderType type;
    private EatInOrderStatus status;
    private EatInOrderLineItems orderLineItems;
    private OrderTableId orderTableId;
    private LocalDateTime orderDateTime;

    public static EatInOrder create(OrderLineItems orderLineItems, OrderTableId orderTableId) {
        return new EatInOrder(
                OrderId.generate(),
                OrderStatus.WAITING,
                orderLineItems,
                orderTableId,
                LocalDateTime.now()
        );
    }

    public EatInOrder(OrderId id, OrderStatus status, OrderLineItems orderLineItems, OrderTableId orderTableId, LocalDateTime orderDateTime) {
        this(id, EatInOrderStatus.from(status), new EatInOrderLineItems(orderLineItems), orderTableId, orderDateTime);
    }

    public EatInOrder(final OrderId id, EatInOrderStatus status, EatInOrderLineItems orderLineItems, OrderTableId orderTableId, LocalDateTime orderDateTime) {
        this.id = id;
        this.type = OrderType.EAT_IN;
        this.status = status;
        this.orderLineItems = orderLineItems;
        this.orderTableId = orderTableId;
        this.orderDateTime = orderDateTime;
    }

    public void createOrder(OrderTable orderTable) {
        if (!orderTable.isOccupied()) {
            throw new InvalidOrderTableException("손님이 없는 주문 테이블은 주문을 받을 수 없습니다");
        }
        this.status = EatInOrderStatus.WAITING;
        this.orderDateTime = LocalDateTime.now();
        this.orderTableId = orderTable.getId();
    }

    public void accept() {
        if (this.status != EatInOrderStatus.WAITING) {
            throw new InvalidOrderStatusException("접수 대기 중인 주문만 접수 가능합니다");
        }
        this.status = EatInOrderStatus.ACCEPTED;
    }

    public void serve() {
        if (this.status != EatInOrderStatus.ACCEPTED) {
            throw new InvalidOrderStatusException("접수한 주문만 서빙 가능합니다");
        }
        this.status = EatInOrderStatus.SERVED;
    }

    public void complete() {
        if (this.status != EatInOrderStatus.SERVED) {
            throw new InvalidOrderStatusException("서빙된 주문만 완료 가능합니다");
        }
        this.status = EatInOrderStatus.COMPLETED;
        Events.raise(new OrderCompleteEvent(getId()));
    }

    public OrderEntity toEntity() {
        return new OrderEntity(
                id,
                type,
                status.get(),
                orderLineItems.get(),
                null,
                orderTableId,
                orderDateTime
        );
    }

    public OrderId getId() {
        return id;
    }

    public OrderType getType() {
        return type;
    }

    public EatInOrderStatus getStatus() {
        return status;
    }

    public EatInOrderLineItems getOrderLineItems() {
        return orderLineItems;
    }

    public OrderTableId getOrderTableId() {
        return orderTableId;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }
}
