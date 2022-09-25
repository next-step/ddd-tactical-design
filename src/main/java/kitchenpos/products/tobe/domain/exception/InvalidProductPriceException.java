package kitchenpos.products.tobe.domain.exception;

public class InvalidProductPriceException extends IllegalArgumentException {
    private static final String MESSAGE = "상품의 가격은 0원 이상이여야 합니다.";

    public InvalidProductPriceException() {
        super(MESSAGE);
    }
}
