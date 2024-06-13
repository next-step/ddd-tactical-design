package kitchenpos.products.ui.error;

import kitchenpos.shared.error.ErrorCode;
import org.springframework.http.HttpStatus;

import static kitchenpos.products.ui.error.ProductErrorMessages.PRODUCT_NOT_FOUND_MESSAGE;

public enum ProductErrorCode implements ErrorCode {

    PRODUCT_NOT_FOUND(PRODUCT_NOT_FOUND_MESSAGE, HttpStatus.NOT_FOUND);

    ProductErrorCode(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    private final String message;
    private final HttpStatus httpStatus;

    @Override
    public HttpStatus getStatus() {
        return this.httpStatus;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
