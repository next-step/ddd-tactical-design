package kitchenpos.menus.exception;

public class MenuProductQuantityException extends RuntimeException {
    private final MenuErrorCode message;

    public MenuProductQuantityException(MenuErrorCode message) {
        this.message = message;
    }

    public String getMessage() {
        return message.getMessage();
    }
}
