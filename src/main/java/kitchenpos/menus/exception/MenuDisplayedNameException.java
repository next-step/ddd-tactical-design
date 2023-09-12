package kitchenpos.menus.exception;

public class MenuDisplayedNameException extends RuntimeException {
    private final MenuErrorCode message;

    public MenuDisplayedNameException(MenuErrorCode message) {
        this.message = message;
    }

    public String getMessage() {
        return message.getMessage();
    }
}
