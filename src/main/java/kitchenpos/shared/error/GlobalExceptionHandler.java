package kitchenpos.shared.error;

import kitchenpos.products.ui.error.ProductErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Object> handleNoSuchElementException() {
        ProductErrorCode errorCode = ProductErrorCode.PRODUCT_NOT_FOUND;
        return ResponseEntity.status(errorCode.getStatus())
                .body(createErrorResponse(errorCode.name(), errorCode.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(createErrorResponse("BAD_REQUEST", "잘못된 요청입니다"));
    }

    private ErrorResponse createErrorResponse(String code, String message) {
        return new ErrorResponse(code, message);
    }
}
