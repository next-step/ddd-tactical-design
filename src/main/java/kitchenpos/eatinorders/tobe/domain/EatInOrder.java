package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.eatinorders.tobe.domain.common.*;
import kitchenpos.eatinorders.tobe.domain.exception.InvalidOrderLineItemsException;
import kitchenpos.eatinorders.tobe.domain.exception.InvalidOrderStatusException;
import kitchenpos.eatinorders.tobe.domain.exception.InvalidOrderTableException;

import java.time.LocalDateTime;

/*
매장 내 식사 주문의
비즈니스 로직을 다루는 클래스
*/

public class EatInOrder {

    private final OrderEntity order;

    public EatInOrder(final OrderEntity order) {
        this.order = order;
        this.order.initializeType(OrderType.EAT_IN);
        validate(order);
    }

    private void validate(OrderEntity order) {
        if (order.orderLineItems().hasNegativeQuantity()) {
            throw new InvalidOrderLineItemsException("주문한 메뉴 수량이 음수면 안됩니다");
        }
    }

    public void createOrder(OrderTable orderTable) {
        if (!orderTable.isOccupied()) {
            throw new InvalidOrderTableException("손님이 없는 주문 테이블은 주문을 받을 수 없습니다");
        }

        order.changeStatus(OrderStatus.WAITING);
        order.changeOrderDateTime(LocalDateTime.now());
        order.changeOrderTableId(orderTable.getId());
    }

    public void accept() {
        if (order.status() != OrderStatus.WAITING) {
            throw new InvalidOrderStatusException("접수 대기 중인 주문만 접수 가능합니다");
        }
        order.changeStatus(OrderStatus.ACCEPTED);
    }

    public void serve() {
        if (order.status() != OrderStatus.ACCEPTED) {
            throw new InvalidOrderStatusException("접수한 주문만 서빙 가능합니다");
        }
        order.changeStatus(OrderStatus.SERVED);
    }

    public void complete() {
        if (order.status() != OrderStatus.SERVED) {
            throw new InvalidOrderStatusException("서빙된 주문만 완료 가능합니다");
        }
        order.changeStatus(OrderStatus.COMPLETED);
    }

    public OrderEntity toEntity() {
        return order;
    }

    public OrderId getId() {
        return toEntity().id();
    }

    public OrderType getType(){
        return toEntity().type();
    }

    public OrderStatus getStatus() {
        return toEntity().status();
    }

    public OrderLineItems getOrderLineItems() {
        return toEntity().orderLineItems();
    }

    public OrderTableId getOrderTableId() {
        return toEntity().orderTableId();
    }

    public LocalDateTime getOrderDateTime() {
        return toEntity().orderDateTime();
    }
}
