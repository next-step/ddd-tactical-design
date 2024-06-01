package kitchenpos.eatinorders.exception;

public enum OrderExceptionCode {
    INVALID_ORDER_TABLE_NAME("주문테이블 이름은 비워둘 수 없습니다.");

    private final String message;

    OrderExceptionCode(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}
