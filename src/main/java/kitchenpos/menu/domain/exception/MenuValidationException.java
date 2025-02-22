package kitchenpos.menu.domain.exception;

public class MenuValidationException extends IllegalArgumentException {
    public MenuValidationException(String message) {
        super(message);
    }
}
