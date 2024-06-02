package kitchenpos.eatinorders.exception;

public enum KitchenPosExceptionMessage {
    INVALID_ORDER_TABLE_NAME("주문테이블 이름은 비워둘 수 없습니다."),
    INVALID_ORDER_TABLE_GUESTS_NUMBER("주문테이블에 착석한 고객의 수는 0명 이상입니다. Invalid value = %s"),
    ORDER_TABLE_UNOCCUPIED_STATUS("주문테이블이 점유해제 상태이므로 착석한 고객의 수를 변경할 수 없습니다. OrderTableID = %s"),
    ORDER_TABLE_CONTAINS_INVALID_ORDER("해당 주문 테이블을 초기화 할 수 없는 주문의 주문상태가 포함되었습니다. OrderTableID = %s"),
    ;

    private final String message;

    KitchenPosExceptionMessage(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}
