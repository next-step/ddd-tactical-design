package kitchenpos.eatinorders.tobe.domain.order;


public enum EatInOrderStatus {
    WAITING,
    ACCEPTED,
    SERVED,
    COMPLETED;

    public EatInOrderStatus accepted() {
        if (this == WAITING) {
            return ACCEPTED;
        }
        throw new IllegalStateException();
    }

    public EatInOrderStatus served() {
        if (this == ACCEPTED) {
            return SERVED;
        }
        throw new IllegalStateException();
    }

    public EatInOrderStatus completed() {
        if (this == SERVED) {
            return COMPLETED;
        }
        throw new IllegalStateException();
    }
}
