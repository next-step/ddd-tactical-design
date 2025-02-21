package kitchenpos.products.tobe.domain.exception;

public class InvalidDisplayedNameException extends RuntimeException {
    private static final String MESSAGE = "상품명은 필수로 입력해야 합니다.";

    public InvalidDisplayedNameException() {
        super(MESSAGE);
    }
}
