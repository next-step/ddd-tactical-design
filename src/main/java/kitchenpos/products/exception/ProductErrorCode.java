package kitchenpos.products.exception;

public enum ProductErrorCode {

    NAME_CONTAINS_PROFANITY("비속어가 포함된 상품명은 사용할 수 없습니다."),
    NAME_IS_NOT_EMPTY_OR_NULL("상품명은 비어있거나 null 일 수 없습니다."),
    PRICE_IS_NEGATIVE("상품 가격은 음수가 될 수 없습니다."),
    PRICE_IS_NULL("상품 가격은 null 일 수 없습니다.")
    ;

    String message;

    ProductErrorCode(String Message) {
        this.message = Message;
    }

    public String getMessage() {
        return message;
    }
}
