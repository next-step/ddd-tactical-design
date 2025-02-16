package kitchenpos.product.application.port.in;

import kitchenpos.product.application.service.model.CreateProductRequest;
import kitchenpos.product.domain.model.Product;

public interface CreateProductUseCase {
    Product create(final CreateProductRequest request);
}
