package kitchenpos.products.exception;

public class ProductNameException extends RuntimeException {

    private static final String MESSAGE = "제품 이름에 비속어(%s)가 포함되었습니다.";

    public ProductNameException(String name) {
        super(String.format(MESSAGE, name));
    }
}
