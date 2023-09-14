package kitchenpos.product.tobe.domain;

public class IllegalNewProductNameException extends IllegalArgumentException {
    public IllegalNewProductNameException() {
    }

    public IllegalNewProductNameException(String s) {
        super(s);
    }

    public IllegalNewProductNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalNewProductNameException(Throwable cause) {
        super(cause);
    }
}
