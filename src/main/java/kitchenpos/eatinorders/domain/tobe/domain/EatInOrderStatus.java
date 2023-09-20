package kitchenpos.eatinorders.domain.tobe.domain;

import java.util.stream.Stream;

public enum EatInOrderStatus {

    WAITING(1),
    ACCEPTED(2),
    SERVED(3),
    COMPLETED(4);

    private int order;

    EatInOrderStatus(int order) {
        this.order = order;
    }

    public EatInOrderStatus nextStatus() {
        return Stream.of(values())
            .filter(status -> status.order > this.order)
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("주문 상태가 잘못 되었습니다."));
    }

    public static EatInOrderStatus initialOrderStatus() {
        return WAITING;
    }
}
