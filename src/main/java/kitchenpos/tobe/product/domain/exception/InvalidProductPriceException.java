package kitchenpos.tobe.product.domain.exception;

public class InvalidProductPriceException extends IllegalArgumentException {
    public InvalidProductPriceException() {
        super("상품 가격은 0보다 커야 합니다.");
    }
}
