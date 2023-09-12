package kitchenpos.common.exception;

public enum PriceErrorCode {

    PRICE_IS_GREATER_THAN_EQUAL_ZERO("가격은 0과 같거나 커야 합니다."),
    PRICE_IS_NULL("가격이 비어 있습니다.");

    private final String message;

    PriceErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
