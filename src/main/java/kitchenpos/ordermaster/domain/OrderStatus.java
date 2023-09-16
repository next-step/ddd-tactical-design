package kitchenpos.ordermaster.domain;

public enum OrderStatus {
    WAITING, ACCEPTED, SERVED, DELIVERING, DELIVERED, COMPLETED;

    public static OrderStatus initialOrderStatus() {
        return WAITING;
    }
}
