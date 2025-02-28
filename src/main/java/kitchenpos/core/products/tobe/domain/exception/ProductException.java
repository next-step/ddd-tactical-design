package kitchenpos.core.products.tobe.domain.exception;

import kitchenpos.core.foundation.exception.SystemException;

public class ProductException extends SystemException {
    public ProductException(String format, Object... args) {
        super(format, args);
    }

    public ProductException(String message, Throwable cause) {
        super(message, cause);
    }

}
