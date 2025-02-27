package kitchenpos.core.products.tobe.domain;

import kitchenpos.core.shared.identifier.ProductId;

public interface ProductIdGenerator {
    ProductId generateId();
}
