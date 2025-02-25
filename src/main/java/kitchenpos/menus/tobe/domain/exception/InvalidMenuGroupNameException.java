package kitchenpos.menus.tobe.domain.exception;

public class InvalidMenuGroupNameException extends IllegalArgumentException {

    public InvalidMenuGroupNameException() {
    }

    public InvalidMenuGroupNameException(String message) {
        super(message);
    }
}
