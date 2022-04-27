package kitchenpos.eatinorders.tobe.domain;

public class InvalidOrderLineException extends RuntimeException {

    private static final long serialVersionUID = 4860545663528575593L;

    public InvalidOrderLineException(String message) {
        super(message);
    }
}
