package kitchenpos.order.tobe.eatinorder.domain;

import java.util.Arrays;

public enum OrderType {
    DELIVERY,
    TAKEOUT,
    EAT_IN;

    public static OrderType of(String orderType) {
        return OrderType.valueOf(Arrays.stream(values()).map(OrderType::name)
                .filter(name -> name.equals(orderType))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("주문 유형이 존재하지 않습니다.")));
    }
}
