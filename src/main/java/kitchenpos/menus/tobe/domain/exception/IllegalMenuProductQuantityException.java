package kitchenpos.menus.tobe.domain.exception;

public class IllegalMenuProductQuantityException extends IllegalArgumentException{

    private static final String DEFAULT_MESSAGE = "메뉴 상품의 가격은 반드시 1 이상이여야 합니다..";

    public IllegalMenuProductQuantityException() {
        super(DEFAULT_MESSAGE);
    }

    public IllegalMenuProductQuantityException(String message) {
        super(message);
    }
}
