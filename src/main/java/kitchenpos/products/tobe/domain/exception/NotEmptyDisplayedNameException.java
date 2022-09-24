package kitchenpos.products.tobe.domain.exception;

public class NotEmptyDisplayedNameException extends IllegalArgumentException {

    public NotEmptyDisplayedNameException() {
        super("상품명은 null거나 공백일 수 없습니다.");
    }

}
