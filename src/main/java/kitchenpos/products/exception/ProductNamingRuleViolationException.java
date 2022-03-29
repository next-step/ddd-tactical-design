package kitchenpos.products.exception;

public class ProductNamingRuleViolationException extends RuntimeException{

    public ProductNamingRuleViolationException() {
        super();
    }

    public ProductNamingRuleViolationException(String message) {
        super(message);
    }

    public ProductNamingRuleViolationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductNamingRuleViolationException(Throwable cause) {
        super(cause);
    }

    protected ProductNamingRuleViolationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
