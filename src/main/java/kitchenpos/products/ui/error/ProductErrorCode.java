package kitchenpos.products.ui.error;

import kitchenpos.shared.error.ErrorCode;

import static kitchenpos.products.ui.error.ProductErrorMessages.PRODUCT_NOT_FOUND_MESSAGE;

public enum ProductErrorCode implements ErrorCode {

    PRODUCT_NOT_FOUND(PRODUCT_NOT_FOUND_MESSAGE);

    ProductErrorCode(String message) {
        this.message = message;
    }

    private final String message;

    @Override
    public String getMessage() {
        return this.message;
    }
}
