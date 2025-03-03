package kitchenpos.eatinorders.tobe.domain.order.vo;

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
        throw new IllegalStateException();
    }

    public EatInOrderStatus served() {
        if (ACCEPTED == this) {
            return SERVED;
        }
        throw new IllegalStateException();
    }

    public EatInOrderStatus completed() {
        if (SERVED == this) {
            return COMPLETED;
        }
        throw new IllegalStateException();
    }

    public boolean isSameStatus(final EatInOrderStatus orderStatus) {
        return this == orderStatus;
    }

    public boolean isSameStatus(final String orderStatus) {
        return this.name().equalsIgnoreCase(orderStatus);
    }
}
