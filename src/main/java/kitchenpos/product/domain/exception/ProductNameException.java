package kitchenpos.product.domain.exception;

import kitchenpos.global.exception.ErrorCode;

public class ProductNameException extends IllegalArgumentException {
    public ProductNameException() {
        super(ErrorCode.PRODUCT_NAME_NOT_ALLOWED.toString());
    }
}
