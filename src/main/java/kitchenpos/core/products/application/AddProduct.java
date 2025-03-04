package kitchenpos.core.products.application;

import kitchenpos.core.products.application.dto.CreateProductRequest;
import kitchenpos.core.products.tobe.domain.Product;

public interface AddProduct {
    Product addProduct(CreateProductRequest request);
}
