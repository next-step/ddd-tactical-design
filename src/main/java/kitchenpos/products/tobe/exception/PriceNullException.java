package kitchenpos.products.tobe.exception;

public class PriceNullException extends IllegalArgumentException {

    private static final String PRICE_SHOULD_NOT_BE_NULL = "가격은 null이 될 수 없습니다.";

    public PriceNullException() {
        super(PRICE_SHOULD_NOT_BE_NULL);
    }

    public PriceNullException(final String s) {
        super(s);
    }

}
