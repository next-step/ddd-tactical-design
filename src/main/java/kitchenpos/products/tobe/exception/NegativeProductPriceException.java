package kitchenpos.products.tobe.exception;

public class NegativeProductPriceException extends IllegalArgumentException {

    public NegativeProductPriceException() {
        super("상품의 가격은 0원보다 커야합니다.");
    }

}
