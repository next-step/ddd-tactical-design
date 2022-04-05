package kitchenpos.common.exception;

public class NamingRuleViolationException extends RuntimeException {

    public NamingRuleViolationException() {
        super();
    }

    public NamingRuleViolationException(String message) {
        super(message);
    }

    public NamingRuleViolationException(String message, Throwable cause) {
        super(message, cause);
    }

    public NamingRuleViolationException(Throwable cause) {
        super(cause);
    }

    protected NamingRuleViolationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
