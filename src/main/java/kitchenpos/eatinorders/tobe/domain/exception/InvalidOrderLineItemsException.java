package kitchenpos.eatinorders.tobe.domain.exception;

public class InvalidOrderLineItemsException extends IllegalArgumentException {
    public InvalidOrderLineItemsException() {
    }

    public InvalidOrderLineItemsException(String message) {
        super(message);
    }

}
