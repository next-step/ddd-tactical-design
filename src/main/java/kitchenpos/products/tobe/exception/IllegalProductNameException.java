package kitchenpos.products.tobe.exception;

public class IllegalProductNameException extends IllegalArgumentException {

    private static final String DEFAULT_MESSAGE = "상품의 이름이 올바르지 않거나 금지어를 포함하고 있습니다.";

    public IllegalProductNameException() {
        super(DEFAULT_MESSAGE);
    }

    public IllegalProductNameException(String message) {
        super(message);
    }
}
