package kitchenpos.products.tobe.exception;

public class InvalidPriceException extends IllegalArgumentException {
    public InvalidPriceException(String message) {
        super(message);
    }

    public InvalidPriceException() {
        super("가격은 0 이상이어야 합니다.");
    }
}
