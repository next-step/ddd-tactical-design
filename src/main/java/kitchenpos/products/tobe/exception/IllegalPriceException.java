package kitchenpos.products.tobe.exception;

public class IllegalPriceException extends IllegalArgumentException {

    private static final String DEFAULT_MESSAGE = "가격이 올바르지 않습니다.";

    public IllegalPriceException() {
        super(DEFAULT_MESSAGE);
    }

    public IllegalPriceException(String message) {
        super(message);
    }
}
