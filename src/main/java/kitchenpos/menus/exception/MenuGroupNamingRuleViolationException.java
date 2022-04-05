package kitchenpos.menus.exception;

public class MenuGroupNamingRuleViolationException extends RuntimeException{

    public MenuGroupNamingRuleViolationException() {
        super();
    }

    public MenuGroupNamingRuleViolationException(String message) {
        super(message);
    }

    public MenuGroupNamingRuleViolationException(String message, Throwable cause) {
        super(message, cause);
    }

    public MenuGroupNamingRuleViolationException(Throwable cause) {
        super(cause);
    }

    protected MenuGroupNamingRuleViolationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
