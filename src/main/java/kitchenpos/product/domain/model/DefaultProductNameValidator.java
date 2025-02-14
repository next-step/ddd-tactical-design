package kitchenpos.product.domain.model;

import kitchenpos.product.domain.exception.ProductNameValidationException;

public class DefaultProductNameValidator implements ProductNameValidator {
    private static final String MESSAGE_PRODUCT_NAME_REQUIRED = "상품명을 입력해주세요.";

    @Override
    public void validate(String name) {
        if (name == null || name.isBlank()) {
            throw new ProductNameValidationException(MESSAGE_PRODUCT_NAME_REQUIRED);
        }
    }
}
