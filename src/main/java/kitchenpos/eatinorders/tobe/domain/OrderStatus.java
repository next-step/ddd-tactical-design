package kitchenpos.eatinorders.tobe.domain;

public enum OrderStatus {

    WAITING,
    ACCEPTED,
    SERVED,
    COMPLETED;

    public OrderStatus accept() {
        if (WAITING != this) {
            throw new IllegalStateException();
        }

        return OrderStatus.ACCEPTED;
    }

    public OrderStatus serve() {
        if (ACCEPTED != this) {
            throw new IllegalStateException();
        }

        return OrderStatus.SERVED;
    }

    public OrderStatus complete() {
        if (SERVED != this) {
            throw new IllegalStateException();
        }

        return OrderStatus.COMPLETED;
    }
}
