package kitchenpos.products.tobe.exception.message;

public enum ProductErrorCode {

    PRICE_IS_GREATER_THAN_EQUAL_ZERO("가격은 0보다 커야 합니다.")
    , NAME_IS_NULL_OR_EMPTY("이름이 없거나 공백입니다. 다시 입력해주세요.")
    , NAME_HAS_PROFANITY("비속어를 빼고 입력해주세요.")
    ;
    private String message;

    ProductErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
