package kitchenpos.global.error.dto;


import kitchenpos.global.error.ErrorCode;

public record ErrorResponse(int status, String message) {

    public ErrorResponse(final ErrorCode errorCode) {
        this(errorCode.getHttpStatus().value(), errorCode.getMessage());
    }

    public ErrorResponse(final ErrorCode errorCode, final String customMessage) {
        this(errorCode.getHttpStatus().value(), customMessage);
    }
}
