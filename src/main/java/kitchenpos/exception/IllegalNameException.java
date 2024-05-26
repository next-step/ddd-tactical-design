package kitchenpos.exception;

public class IllegalNameException extends IllegalArgumentException{
    public IllegalNameException(String s) {
        super(s + " : 잘못된 이름입니다.");
    }
}
