package kitchenpos.menus.domain.tobe.menugroup;

public class MenuGroupNotFoundException extends RuntimeException {

    public MenuGroupNotFoundException() {
    }

    public MenuGroupNotFoundException(String message) {
        super(message);
    }

    public MenuGroupNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public MenuGroupNotFoundException(Throwable cause) {
        super(cause);
    }
}
