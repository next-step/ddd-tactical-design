package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.eatinorders.tobe.domain.common.OrderEntity;
import kitchenpos.eatinorders.tobe.domain.common.OrderStatus;
import kitchenpos.eatinorders.tobe.domain.common.OrderType;
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

    public void changeStatus() {
        switch (order.status()) {
            case WAITING -> order.changeStatus(OrderStatus.ACCEPTED);
            case ACCEPTED -> order.changeStatus(OrderStatus.SERVED);
            case SERVED -> order.changeStatus(OrderStatus.COMPLETED);
            default -> throw new InvalidOrderStatusException("잘못된 주문 상태입니다");
        }
    }

    public OrderEntity toEntity() {
        return order;
    }
}
