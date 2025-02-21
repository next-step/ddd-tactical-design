package kitchenpos.common.exception;

public enum ExceptionDetails {

    PRICE_LESS_THAN_ZERO_EXCEPTION("상품의 가격은 0원 이상이어야 합니다."),
    DISPLAYED_NAME_EMPTY_EXCEPTION("상품의 이름은 비어있을 수 없습니다."),
    DISPLAYED_NAME_INCLUDE_PROFANITY_EXCEPTION("상품의 이름은 비속어를 포함할 수 없습니다.");

    private final String message;

    ExceptionDetails(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
