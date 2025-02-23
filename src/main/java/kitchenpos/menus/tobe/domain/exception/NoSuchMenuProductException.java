package kitchenpos.menus.tobe.domain.exception;

public class NoSuchMenuProductException extends RuntimeException {
    private static final String MESSAGE = "메뉴 상품이 존재하지 않습니다.";

    public NoSuchMenuProductException() {
        super(MESSAGE);
    }
}
