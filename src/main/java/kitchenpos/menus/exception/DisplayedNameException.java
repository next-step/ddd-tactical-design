package kitchenpos.menus.exception;

public class DisplayedNameException extends RuntimeException {
    private final MenuErrorCode message;

    public DisplayedNameException(MenuErrorCode message) {
        this.message = message;
    }

    public String getMessage() {
        return message.getMessage();
    }
}
