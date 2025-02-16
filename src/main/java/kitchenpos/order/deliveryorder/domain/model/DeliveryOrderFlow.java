package kitchenpos.order.deliveryorder.domain.model;

import java.util.Arrays;

public enum DeliveryOrderFlow {
    ACCEPTED(DeliveryOrderStatus.ACCEPTED, DeliveryOrderStatus.WAITING),
    SERVED(DeliveryOrderStatus.SERVED, DeliveryOrderStatus.ACCEPTED),
    DELIVERING(DeliveryOrderStatus.DELIVERING, DeliveryOrderStatus.SERVED),
    DELIVERED(DeliveryOrderStatus.DELIVERED, DeliveryOrderStatus.DELIVERING),
    COMPLETED(DeliveryOrderStatus.COMPLETED, DeliveryOrderStatus.DELIVERED);

    private final DeliveryOrderStatus nextStatus;
    private final DeliveryOrderStatus previousStatus;

    DeliveryOrderFlow(DeliveryOrderStatus nextStatus, DeliveryOrderStatus previousStatus) {
        this.nextStatus = nextStatus;
        this.previousStatus = previousStatus;
    }

    public static DeliveryOrderFlow from(DeliveryOrderStatus status) {
        return valueOf(status.name());
    }

    public boolean validateOrderStatus(DeliveryOrderStatus nextOrderStatus) {
        DeliveryOrderFlow nextStatus = Arrays.stream(values())
                .filter(v -> v.nextStatus == nextOrderStatus)
                .findFirst()
                .orElseThrow();
        return nextStatus.previousStatus != (this.nextStatus);
    }

    public boolean isRiderNecessary(DeliveryOrderStatus orderStatus) {
        return orderStatus == DeliveryOrderStatus.SERVED;
    }
}
