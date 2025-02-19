package kitchenpos.order.eatinorder.exception;

public enum EatInOrderExceptionMessage {
    ORDER_TABLE_NAME_CREATION_EXCEPTION("주문 테이블 이름을 채워주세요!"),
    EMPTY_ORDER_TABLE_EXCEPTION("주문 테이블이 비어있습니다!"),
    NUMBER_OF_GUESTS_EXCEPTION("손님 수가 음수일 수 없습니다!")
    ;


    private final String message;

    EatInOrderExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
