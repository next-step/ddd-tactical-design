package kitchenpos.menu.domain.exception;

public class MenuGroupNameValidationException extends IllegalArgumentException {
    public MenuGroupNameValidationException(String message) {
        super(message);
    }
}
