package kitchenpos.menu.domain.exception;

public class MenuPriceValidationException extends IllegalArgumentException {
    public MenuPriceValidationException(String message) {
        super(message);
    }
}
