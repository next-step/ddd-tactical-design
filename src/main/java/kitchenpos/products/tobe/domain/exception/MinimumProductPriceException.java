package kitchenpos.products.tobe.domain.exception;

public class MinimumProductPriceException extends IllegalArgumentException {

    public MinimumProductPriceException() {
        super("상품 가격은 0원 이상이어야 한다.");
    }
}
