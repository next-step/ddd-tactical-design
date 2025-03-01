package kitchenpos.global.exception;

import java.util.NoSuchElementException;

public class NotFoundException extends NoSuchElementException {
    public NotFoundException(String message) {
        super(message);
    }
}
