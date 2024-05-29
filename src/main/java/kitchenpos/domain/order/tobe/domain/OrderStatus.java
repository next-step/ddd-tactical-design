package kitchenpos.domain.order.tobe.domain;

public enum OrderStatus {
    WAITING, ACCEPTED, SERVED, DELIVERING, DELIVERED, COMPLETED;

    public OrderStatus changeTo(OrderStatus status) {
        if (!changeable(status)) {
            throw new IllegalStateException(String.format("현재 주문 상태 %s는 %s로 천이가 불가합니다.", this.name(), status.name()));
        }
        return status;
    }

    private boolean changeable(OrderStatus status) {
        return switch (status) {
            case WAITING -> false;
            case ACCEPTED -> this == WAITING;
            case SERVED -> this == ACCEPTED;
            case DELIVERING -> this == SERVED;
            case DELIVERED -> this == DELIVERING;
            case COMPLETED -> this == SERVED || this == DELIVERED;
        };
    }
}
