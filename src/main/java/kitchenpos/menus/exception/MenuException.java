package kitchenpos.menus.exception;

public class MenuException extends RuntimeException {
    private final MenuErrorCode message;

    public MenuException(MenuErrorCode message) {
        this.message = message;
    }

    public String getMessage() {
        return message.getMessage();
    }
}