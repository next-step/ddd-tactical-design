package kitchenpos.core.products.tobe.domain;

import kitchenpos.core.products.tobe.domain.support.ProductNameValidationResult;

public interface ProductNamePolicy {
    ProductNameValidationResult validate(String name);
}