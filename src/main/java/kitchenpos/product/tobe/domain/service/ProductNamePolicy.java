package kitchenpos.product.tobe.domain.service;

import kitchenpos.product.tobe.domain.ProductName;

@FunctionalInterface
public interface ProductNamePolicy {

    void validate(ProductName productName);
}
