package kitchenpos.products.tobe.exception;

public class DisplayedNameEmptyException extends IllegalArgumentException {

    public DisplayedNameEmptyException() {
        super("상품의 이름은 빈 값일 수 없습니다.");
    }

}
