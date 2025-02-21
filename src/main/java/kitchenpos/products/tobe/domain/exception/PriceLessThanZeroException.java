package kitchenpos.products.tobe.domain.exception;

public class PriceLessThanZeroException extends RuntimeException {

    public PriceLessThanZeroException(String message) {
        super(message);
    }
}
