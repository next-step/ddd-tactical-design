package kitchenpos.global.exception;

public class EmptyNameException extends IllegalArgumentException {

    public EmptyNameException() {
        super("이름은 공백을 가질 수 없다.");
    }
}
