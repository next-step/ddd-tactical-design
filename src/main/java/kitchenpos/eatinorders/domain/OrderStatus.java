package kitchenpos.eatinorders.domain;

public enum OrderStatus {
    WAITING, ACCEPTED, SERVED, DELIVERING, DELIVERED, COMPLETED;

    public boolean isWaiting() {
        return this==OrderStatus.WAITING;
    }

    public boolean isAccepted() {
        return this==OrderStatus.ACCEPTED;
    }

    public boolean isServed() {
        return this==OrderStatus.SERVED;
    }

    public boolean isDelivering() {
        return this==OrderStatus.DELIVERING;
    }

    public boolean isDelivered() {
        return this==OrderStatus.DELIVERED;
    }

    public boolean isCompleted() {
        return this==OrderStatus.COMPLETED;
    }
}
