package kitchenpos.products.tobe.domain.exception;

public class NullProductPriceException extends IllegalArgumentException {

    public NullProductPriceException() {
        super("등록할 상품 가격이 없다.");
    }
}
