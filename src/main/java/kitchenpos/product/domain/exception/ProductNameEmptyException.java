package kitchenpos.product.domain.exception;

public class ProductNameEmptyException extends IllegalArgumentException {
    private static final String MESSAGE_PRODUCT_NAME_REQUIRED = "상품명을 입력해주세요.";

    public ProductNameEmptyException() {
        super(MESSAGE_PRODUCT_NAME_REQUIRED);
    }
}
