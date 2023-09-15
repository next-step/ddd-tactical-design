package kitchenpos.eatinorders.domain.tobe;

public enum OrderStatus {
    WAITING, ACCEPTED, SERVED, COMPLETED;

    public boolean isWaiting() {
        return this == WAITING;
    }

    public boolean isAcepted() {
        return this == ACCEPTED;
    }

    public boolean isServed() {
        return this == SERVED;
    }

    public boolean isCompleted() {
        return this == COMPLETED;
    }

}
