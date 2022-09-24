package kitchenpos.products.tobe.domain.exception;

public class NotContainsProfanityException extends IllegalArgumentException {

    public NotContainsProfanityException() {
        super("상품명에 비속어를 포함할 수 없습니다.");
    }

}
