package kitchenpos.menus.tobe.domain.exception;

public class InvalidMenuProductQuantityException extends RuntimeException {
    private static final String MESSAGE = "메뉴 상품 수량은 0개 이상이어야 합니다.";

    public InvalidMenuProductQuantityException() {
        super(MESSAGE);
    }
}
