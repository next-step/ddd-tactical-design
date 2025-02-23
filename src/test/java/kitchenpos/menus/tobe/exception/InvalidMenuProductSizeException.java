package kitchenpos.menus.tobe.exception;

public class InvalidMenuProductSizeException extends RuntimeException {
    private static final String MESSAGE = "메뉴 상품은 필수로 존재해야 합니다.";

    public InvalidMenuProductSizeException() {
        super(MESSAGE);
    }
}
