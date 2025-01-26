package kitchenpos.menus.tobe.domain;

public class NotFoundProductException extends IllegalArgumentException {
    public NotFoundProductException() {
        super("상품을 찾을 수가 없습니다.");
    }
}
