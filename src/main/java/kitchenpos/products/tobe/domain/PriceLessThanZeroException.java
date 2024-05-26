package kitchenpos.products.tobe.domain;

public class PriceLessThanZeroException extends RuntimeException {

    public PriceLessThanZeroException(final String message) {
        super(message);
    }
}
