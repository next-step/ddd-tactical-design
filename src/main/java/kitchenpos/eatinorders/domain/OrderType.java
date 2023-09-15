package kitchenpos.eatinorders.domain;

import java.util.Objects;

public enum OrderType {
    DELIVERY, TAKEOUT, EAT_IN;

    public static boolean isValid(OrderType orderType) {
        return !Objects.isNull(orderType);
    }
}
