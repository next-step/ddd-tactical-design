package kitchenpos.eatinorders.exception;

public enum KitchenPosExceptionMessage {
    INVALID_ORDER_TABLE_NAME("주문테이블 이름은 비워둘 수 없습니다."),
    INVALID_ORDER_TABLE_GUESTS_NUMBER("주문테이블에 착석한 고객의 수는 0명 이상입니다. Invalid value = %s"),
    ORDER_TABLE_UNOCCUPIED_STATUS("주문테이블이 점유해제 상태이므로 착석한 고객의 수를 변경할 수 없습니다. ID = %s")
    ;

    private final String message;

    KitchenPosExceptionMessage(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}
