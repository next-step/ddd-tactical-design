package kitchenpos.products.tobe.domain.exception;


import kitchenpos.global.error.DomainException;
import kitchenpos.global.error.ErrorCode;

public class InvalidProductException extends DomainException {
    public InvalidProductException(final String message) {
        super(ErrorCode.INVALID_PRODUCT, message);
    }
}
