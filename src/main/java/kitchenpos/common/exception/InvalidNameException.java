package kitchenpos.common.exception;

public class InvalidNameException extends RuntimeException {

    private static final long serialVersionUID = 6636597789030140212L;

    public InvalidNameException(String message) {
        super(message);
    }
}
