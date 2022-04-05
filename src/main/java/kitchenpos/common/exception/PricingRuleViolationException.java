package kitchenpos.common.exception;

public class PricingRuleViolationException extends RuntimeException {

    public PricingRuleViolationException() {
        super();
    }

    public PricingRuleViolationException(String message) {
        super(message);
    }

    public PricingRuleViolationException(String message, Throwable cause) {
        super(message, cause);
    }

    public PricingRuleViolationException(Throwable cause) {
        super(cause);
    }

    protected PricingRuleViolationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
