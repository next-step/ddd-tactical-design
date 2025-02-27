package kitchenpos.core.products.application;

import kitchenpos.core.shared.identifier.ProductId;

public interface AddProduct {
    ProductId add(CreateProductRequest request);
}
