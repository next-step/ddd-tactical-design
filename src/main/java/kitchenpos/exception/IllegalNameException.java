package kitchenpos.exception;

public class IllegalNameException extends IllegalArgumentException{
    public IllegalNameException(String descriptions, String name) {
        super(descriptions + " : " + name);
    }
}
