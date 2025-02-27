package kitchenpos.product.exception;

public enum ProductExceptionMessage {

    PRODUCT_NAME_CREATION_EXCEPTION("상품 이름을 채워주세요!"),
    PRODUCT_NAME_VALIDATION_EXCEPTION("상품 이름에 비속어가 존재합니다. 비속어를 제외해주세요!"),
    PRODUCT_PRICE_CREATION_EXCEPTION("상품 가격을 채워주세요!");

    private final String message;

    ProductExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
