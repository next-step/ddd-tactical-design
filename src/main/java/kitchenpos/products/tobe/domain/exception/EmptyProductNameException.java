package kitchenpos.products.tobe.domain.exception;

public class EmptyProductNameException extends IllegalArgumentException {

    public EmptyProductNameException() {
        super("상품 이름은 공백을 가질 수 없다.");
    }
}
