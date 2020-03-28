package kitchenpos.menus.tobe.domain.menugroup.exception;

public class MenuGroupNotFoundException extends RuntimeException {

    public MenuGroupNotFoundException() {
    }

    public MenuGroupNotFoundException(String message) {
        super(message);
    }
}
