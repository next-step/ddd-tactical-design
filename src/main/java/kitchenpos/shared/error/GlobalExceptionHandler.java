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
    public ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException e) {
        ProductErrorCode errorCode = ProductErrorCode.PRODUCT_NOT_FOUND;
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(createErrorResponse(errorCode.name(), errorCode.getMessage()));
    }

    private ErrorResponse createErrorResponse(String code, String message) {
        return new ErrorResponse(code, message);
    }
}
