package kitchenpos.order.eatinorder.exception;

public enum EatInOrderExceptionMessage {
    ORDER_TABLE_NAME_CREATION_EXCEPTION("주문 테이블 이름을 채워주세요!");

    private final String message;

    EatInOrderExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
