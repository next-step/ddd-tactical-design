package kitchenpos.products.tobe.exception;

public class ProductNameRequiredException extends IllegalArgumentException {
    public ProductNameRequiredException(String message) {
        super(message);
    }

    public ProductNameRequiredException() {
        super("상품명은 필수로 입력해야 합니다.");
    }
}
