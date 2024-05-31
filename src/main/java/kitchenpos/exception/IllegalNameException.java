package kitchenpos.exception;

public class IllegalNameException extends IllegalArgumentException{
    public IllegalNameException(String name) {
        super("잘못된 이름입니다. " + name);
    }
}
