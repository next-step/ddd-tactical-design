package kitchenpos.menus.exception;

import java.util.NoSuchElementException;

public class MenuNotFoundException extends NoSuchElementException {

    public MenuNotFoundException(String message) {
        super(message);
    }
}
