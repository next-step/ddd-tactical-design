package kitchenpos.products.tobe.domain.exception;

public class InvalidPricePeriodException extends RuntimeException {
    private static final String MESSAGE = "상품의 가격은 0원 이상이어야 합니다.";

    public InvalidPricePeriodException() {
        super(MESSAGE);
    }
}
