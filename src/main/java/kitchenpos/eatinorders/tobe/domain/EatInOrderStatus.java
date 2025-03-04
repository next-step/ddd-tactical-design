package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.eatinorders.tobe.domain.common.OrderStatus;
import kitchenpos.eatinorders.tobe.domain.exception.InvalidOrderStatusException;

import java.util.Arrays;

/*
매장 내 주문의 주문 상태를 관리
 */

public enum EatInOrderStatus {
    WAITING(OrderStatus.WAITING),
    ACCEPTED(OrderStatus.ACCEPTED),
    SERVED(OrderStatus.SERVED),
    COMPLETED(OrderStatus.COMPLETED);

    private final OrderStatus status;

    public static EatInOrderStatus from(OrderStatus status) {
        return Arrays.stream(values())
                .filter(eatInOrderStatus -> status == eatInOrderStatus.status)
                .findFirst().orElseThrow(() -> new InvalidOrderStatusException("매장 내 식사 주문에 해당되는 상태 값이 아닙니다"));
    }

    EatInOrderStatus(OrderStatus status) {
        this.status = status;
    }

    public OrderStatus get() {
        return status;
    }
}
