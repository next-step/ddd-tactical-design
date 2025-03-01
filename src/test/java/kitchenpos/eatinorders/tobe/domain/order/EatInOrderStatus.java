package kitchenpos.eatinorders.tobe.domain.order;

import kitchenpos.eatinorders.domain.OrderStatus;

import java.util.Arrays;

public enum EatInOrderStatus {
    WAITING, ACCEPTED, SERVED, COMPLETED;

    public static EatInOrderStatus of(final String eatInOrderStatus) {
        return Arrays.stream(values())
                .filter(status -> status.isSameStatus(eatInOrderStatus))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

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

    public boolean isSameStatus(final String orderStatus) {
        return this.name().equalsIgnoreCase(orderStatus);
    }
}
