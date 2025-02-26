package kitchenpos.products.application.exception;


import kitchenpos.global.error.BusinessException;
import kitchenpos.global.error.ErrorCode;

public class InvalidProductServiceException extends BusinessException {
    public InvalidProductServiceException(final String message) {
        super(ErrorCode.INVALID_PRODUCT, message);
    }
}
