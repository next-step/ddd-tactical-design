package kitchenpos.products.tobe.domain;

public class PriceValidateException extends IllegalArgumentException {
    public PriceValidateException(String message) {
        super(message);
    }
}
