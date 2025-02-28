package kitchenpos.core.foundation.exception;

import org.springframework.context.MessageSourceResolvable;

public class SystemException extends RuntimeException implements MessageSourceResolvable {

    public SystemException(String format, Object... args) {
        super(String.format(format, args));
    }

    public SystemException(Throwable cause) {
        super(cause);
    }

    public SystemException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String[] getCodes() {
        return new String[]{"Exception." + getClass().getSimpleName()};
    }

    @Override
    public Object[] getArguments() {
        return new Object[0];
    }

    @Override
    public String getDefaultMessage() {
        return getMessage();
    }

}
