package kitchenpos.global.error;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    // product
    INVALID_PRODUCT(HttpStatus.BAD_REQUEST, "The product information is invalid."),
    // common
    INVALID_DTO_FIELD(HttpStatus.BAD_REQUEST, "The DTO field is incorrect."),
    INVALID_REQUEST_BODY(HttpStatus.BAD_REQUEST, "The RequestBody format is incorrect."),
    INVALID_REQUEST_PARAM(HttpStatus.BAD_REQUEST, "The RequestParam format is incorrect."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred on the server.");

    private final HttpStatus httpStatus;
    private final String message;


    ErrorCode(final HttpStatus httpStatus, final String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }
}
