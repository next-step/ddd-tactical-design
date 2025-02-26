package kitchenpos.global.error;

import org.springframework.http.HttpStatus;

public abstract class DomainException extends RuntimeException {
    private final ErrorCode errorCode;

    protected DomainException(final ErrorCode errorCode, final String message) {
        super(message);
        this.errorCode = errorCode;
    }

    protected DomainException(final ErrorCode errorCode) {
        this(errorCode, errorCode.getMessage());
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public HttpStatus getHttpStatus() {
        return errorCode.getHttpStatus();
    }
}
