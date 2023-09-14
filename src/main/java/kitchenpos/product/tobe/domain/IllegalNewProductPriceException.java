package kitchenpos.product.tobe.domain;

public class IllegalNewProductPriceException extends IllegalArgumentException {
    public IllegalNewProductPriceException() {
    }

    public IllegalNewProductPriceException(String s) {
        super(s);
    }

    public IllegalNewProductPriceException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalNewProductPriceException(Throwable cause) {
        super(cause);
    }
}
