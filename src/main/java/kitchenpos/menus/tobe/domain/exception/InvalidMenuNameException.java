package kitchenpos.menus.tobe.domain.exception;

public class InvalidMenuNameException extends IllegalArgumentException {
    public InvalidMenuNameException() {
    }

    public InvalidMenuNameException(String message) {
        super(message);
    }
}
