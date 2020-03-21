package kitchenpos.common;

public class WrongNameException extends RuntimeException {
    public WrongNameException() {
    }

    public WrongNameException(String message) {
        super(message);
    }
}
