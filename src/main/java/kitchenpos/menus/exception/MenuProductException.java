package kitchenpos.menus.exception;

public class MenuProductException extends RuntimeException {
    private final MenuErrorCode message;

    public MenuProductException(MenuErrorCode message) {
        this.message = message;
    }

    public String getMessage() {
        return message.getMessage();
    }
}
