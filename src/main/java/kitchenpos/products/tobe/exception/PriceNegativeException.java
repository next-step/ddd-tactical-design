package kitchenpos.products.tobe.exception;

public class PriceNegativeException extends IllegalArgumentException {

    private static final String PRICE_SHOULD_NOT_BE_NEGATIVE = "가격은 음수가 될 수 없습니다.";

    public PriceNegativeException() {
        super(PRICE_SHOULD_NOT_BE_NEGATIVE);
    }

    public PriceNegativeException(final String s) {
        super(s);
    }

}
