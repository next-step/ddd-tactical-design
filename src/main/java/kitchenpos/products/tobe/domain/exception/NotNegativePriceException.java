package kitchenpos.products.tobe.domain.exception;

public class NotNegativePriceException extends IllegalArgumentException {

    public NotNegativePriceException() {
        super("상품 가격은 0원보다 커야합니다.");
    }

}
