package kitchenpos.products.tobe.domain.exception;

public class ProductNameNullException extends RuntimeException {

    private static final String MESSAGE = "이름은 비어있을 수 없습니다.";

    public ProductNameNullException() {
        super(MESSAGE);
    }
}
