package kitchenpos.products.tobe.domain.exception;

public class NegativePriceException extends RuntimeException {

    private static final String MESSAGE = "가격은 0원 이상이어야 합니다.";

    public NegativePriceException() {
        super(MESSAGE);
    }
}
