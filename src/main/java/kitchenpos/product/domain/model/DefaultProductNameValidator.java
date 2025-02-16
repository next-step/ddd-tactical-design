package kitchenpos.product.domain.model;

import kitchenpos.product.domain.exception.ProductNameEmptyException;

public class DefaultProductNameValidator implements ProductNameValidator {
    @Override
    public void validate(String name) {
        if (name == null || name.isBlank()) {
            throw new ProductNameEmptyException();
        }
    }
}
