package kitchenpos.eatinorders.tobe.domain;

public enum OrderStatus {
    WAITING, ACCEPTED, SERVED, COMPLETED;

    public boolean isWaiting() {
        return this == WAITING;
    }

    public boolean isAccepted() {
        return this == ACCEPTED;
    }

    public boolean isServed() {
        return this == SERVED;
    }

}
