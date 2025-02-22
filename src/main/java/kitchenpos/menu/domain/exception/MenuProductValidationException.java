package kitchenpos.menu.domain.exception;

public class MenuProductValidationException extends IllegalArgumentException {
    public MenuProductValidationException(String message) {
        super(message);
    }
}
