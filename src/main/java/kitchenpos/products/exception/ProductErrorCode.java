package kitchenpos.products.exception;

public enum ProductErrorCode {

    PRICE_IS_GREATER_THAN_EQUAL_ZERO("가격은 0보다 커야 합니다."),
    PRICE_IS_NULL("가격이 비어 있습니다."),
    NAME_IS_NULL_OR_EMPTY("이름이 없거나 공백입니다. 다시 입력해주세요."),
    NAME_HAS_PROFANITY("비속어를 빼고 입력해주세요.");
    private final String message;

    ProductErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}