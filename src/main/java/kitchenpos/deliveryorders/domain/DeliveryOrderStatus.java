package kitchenpos.deliveryorders.domain;

import java.util.stream.Stream;

public enum DeliveryOrderStatus {

    WAITING(1),
    ACCEPTED(2),
    SERVED(3),
    DELIVERING(4),
    DELIVERED(5),

    COMPLETED(6);

    private int order;

    DeliveryOrderStatus(int order) {
        this.order = order;
    }

    public DeliveryOrderStatus nextStatus() {
        return Stream.of(values())
            .filter(status -> status.order > this.order)
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("주문 상태가 잘못 되었습니다."));
    }

    public int getOrder() {
        return order;
    }

    public static DeliveryOrderStatus initialOrderStatus() {
        return WAITING;
    }
}
