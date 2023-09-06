package kitchenpos.products.tobe.exception.message;

public enum ProductErrorCode {

    PRICE_IS_GREATER_THAN_EQUAL_ZERO("가격은 0보다 커야 합니다.");
    private String message;

    ProductErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
