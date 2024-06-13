package kitchenpos.products.tobe.exception;

public class ContainsProfanityException extends IllegalArgumentException {

    public ContainsProfanityException() {
        super("상품의 이름에는 비속어가 포함될 수 없습니다.");
    }

}
