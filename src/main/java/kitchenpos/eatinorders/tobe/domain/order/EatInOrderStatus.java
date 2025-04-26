package kitchenpos.eatinorders.tobe.domain.order;


import kitchenpos.eatinorders.tobe.domain.exception.InvalidOrderStatusTransitionException;

public enum EatInOrderStatus {
    WAITING,
    ACCEPTED,
    SERVED,
    COMPLETED;

    public EatInOrderStatus accepted() {
        if (this == WAITING) {
            return ACCEPTED;
        }
        throw new InvalidOrderStatusTransitionException("주문 상태가 '주문 대기(WAITING)'이 아닌 경우에는 주문을 승인할 수 없습니다.");
    }

    public EatInOrderStatus served() {
        if (this == ACCEPTED) {
            return SERVED;
        }
        throw new InvalidOrderStatusTransitionException("주문 상태가 '주문 접수(ACCEPTED)'가 아닌 경우에는 서빙할 수 없습니다.");
    }

    public EatInOrderStatus completed() {
        if (this == SERVED) {
            return COMPLETED;
        }
        throw new InvalidOrderStatusTransitionException("주문 상태가 '서빙 완료(SERVED)'가 아닌 경우에는 주문을 완료할 수 없습니다.");
    }
}
