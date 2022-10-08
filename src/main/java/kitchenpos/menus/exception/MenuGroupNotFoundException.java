package kitchenpos.menus.exception;

import java.util.NoSuchElementException;

public class MenuGroupNotFoundException extends NoSuchElementException {

    public MenuGroupNotFoundException(String message) {
        super(message);
    }
}
