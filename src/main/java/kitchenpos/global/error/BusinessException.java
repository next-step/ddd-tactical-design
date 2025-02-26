// 📌 응용 서비스 예외의 부모 클래스
package kitchenpos.global.error;

import org.springframework.http.HttpStatus;

public abstract class BusinessException extends RuntimeException {
    private final ErrorCode errorCode;

    protected BusinessException(final ErrorCode errorCode, final String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public HttpStatus getHttpStatus() {
        return errorCode.getHttpStatus();
    }
}
