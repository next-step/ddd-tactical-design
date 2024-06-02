package kitchenpos.orders.tobe.domain;

public enum OrderType {
    DELIVERY, TAKEOUT, EAT_IN,
    ;

    public static final String WRONG_ORDER_TYPE_ERROR = "잘못된 주문 타입입니다.";
}
