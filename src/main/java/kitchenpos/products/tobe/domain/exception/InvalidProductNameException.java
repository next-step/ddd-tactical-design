package kitchenpos.products.tobe.domain.exception;

public class InvalidProductNameException extends IllegalArgumentException {
    private static final String MESSAGE = "상품의 이름은 비속어가 포함될 수 없습니다.";

    public InvalidProductNameException() {
        super(MESSAGE);
    }
}
