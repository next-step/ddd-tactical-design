package kitchenpos.product.domain.exception;

import kitchenpos.global.exception.ErrorCode;

public class ProductPriceException extends IllegalArgumentException {
    public ProductPriceException() {
        super(ErrorCode.PRODUCT_PRICE_NOT_ALLOWED.toString());
    }
}
