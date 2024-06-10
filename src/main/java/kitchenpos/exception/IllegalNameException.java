package kitchenpos.exception;

public class IllegalNameException extends IllegalArgumentException{
    public IllegalNameException(String s) {
        super(s);
    }

    public IllegalNameException(String descriptions, String name) {
        super(descriptions + " : " + name);
    }
}
