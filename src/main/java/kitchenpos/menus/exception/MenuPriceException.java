package kitchenpos.menus.exception;

public class MenuPriceException extends RuntimeException {
    private final MenuErrorCode message;

    public MenuPriceException(MenuErrorCode message) {
        this.message = message;
    }

    public String getMessage() {
        return message.getMessage();
    }
}
