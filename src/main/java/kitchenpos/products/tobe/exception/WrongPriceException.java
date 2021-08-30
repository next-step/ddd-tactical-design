package kitchenpos.products.tobe.exception;

public class WrongPriceException extends IllegalArgumentException {

    public static final String PRICE_SHOULD_NOT_BE_NULL = "가격은 null이 될 수 없습니다.";
    public static final String PRICE_SHOULD_NOT_BE_NEGATIVE = "가격은 음수가 될 수 없습니다.";

    public WrongPriceException(final String s) {
        super(s);
    }

}
