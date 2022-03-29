package kitchenpos.products.exception;

public class IllegalProductPricingPolicy extends RuntimeException{

    public IllegalProductPricingPolicy() {
        super();
    }

    public IllegalProductPricingPolicy(String message) {
        super(message);
    }

    public IllegalProductPricingPolicy(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalProductPricingPolicy(Throwable cause) {
        super(cause);
    }

    protected IllegalProductPricingPolicy(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
