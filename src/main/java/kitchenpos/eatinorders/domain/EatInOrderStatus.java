package kitchenpos.eatinorders.domain;

public enum EatInOrderStatus {
    WAITING, ACCEPTED, SERVED, COMPLETED;

    public boolean isWaiting() {
        return this.equals(WAITING);
    }
    public boolean isAccepted() {
        return this.equals(ACCEPTED);
    }
    public boolean isServed() {
        return this.equals(SERVED);
    }

    public boolean isCompleted() {
        return this.equals(COMPLETED);
    }
}
