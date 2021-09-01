package kitchenpos.products.tobe.exception;

public class WrongPriceException extends IllegalArgumentException {
    public static final String PRICE_SHOULD_NOT_BE_NEGATIVE = "price는 0보다 작을 수 없습니다.";

    public WrongPriceException() {
        super(PRICE_SHOULD_NOT_BE_NEGATIVE);
    }

    public WrongPriceException(final String message) {
        super(message);
    }
}
