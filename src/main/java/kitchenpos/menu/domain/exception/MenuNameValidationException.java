package kitchenpos.menu.domain.exception;

public class MenuNameValidationException extends IllegalArgumentException {
    public MenuNameValidationException(String message) {
        super(message);
    }
}
