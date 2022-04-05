package kitchenpos.products.exception;

import kitchenpos.common.exception.NamingRuleViolationException;

public class ProductNamingRuleViolationException extends NamingRuleViolationException {

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
