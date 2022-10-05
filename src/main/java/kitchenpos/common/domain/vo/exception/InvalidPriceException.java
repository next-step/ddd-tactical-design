package kitchenpos.common.domain.vo.exception;

public class InvalidPriceException extends IllegalArgumentException {
    private static final String MESSAGE = "가격은 0원 이상이여야 합니다.";

    public InvalidPriceException() {
        super(MESSAGE);
    }
}
