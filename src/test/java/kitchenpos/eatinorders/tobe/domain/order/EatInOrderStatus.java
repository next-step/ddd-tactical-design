package kitchenpos.eatinorders.tobe.domain.order;

import kitchenpos.eatinorders.domain.OrderStatus;

public enum EatInOrderStatus {
    WAITING, ACCEPTED, SERVED, COMPLETED;

    public EatInOrderStatus accepted() {
        if (WAITING == this) {
            return ACCEPTED;
        }
        throw new IllegalArgumentException();
    }

    public EatInOrderStatus served() {
        if (ACCEPTED == this) {
            return SERVED;
        }
        throw new IllegalArgumentException();
    }

    public EatInOrderStatus completed() {
        if (SERVED == this) {
            return COMPLETED;
        }
        throw new IllegalArgumentException();
    }

    public boolean isSameStatus(final EatInOrderStatus orderStatus) {
        return this == orderStatus;
    }
}
