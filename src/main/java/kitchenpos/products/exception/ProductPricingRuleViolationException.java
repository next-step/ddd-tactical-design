package kitchenpos.products.exception;

import kitchenpos.common.exception.PricingRuleViolationException;

public class ProductPricingRuleViolationException extends PricingRuleViolationException {

    public ProductPricingRuleViolationException() {
        super();
    }

    public ProductPricingRuleViolationException(String message) {
        super(message);
    }

    public ProductPricingRuleViolationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductPricingRuleViolationException(Throwable cause) {
        super(cause);
    }

    protected ProductPricingRuleViolationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
