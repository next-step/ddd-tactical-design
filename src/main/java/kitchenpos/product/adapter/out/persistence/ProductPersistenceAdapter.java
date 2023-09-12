package kitchenpos.product.adapter.out.persistence;

import kitchenpos.product.application.port.out.LoadProductPort;
import kitchenpos.product.application.port.out.UpdateProductPort;
import kitchenpos.product.domain.Product;

import java.util.UUID;

class ProductPersistenceAdapter implements LoadProductPort, UpdateProductPort {
    @Override
    public Product loadProduct(UUID id) {
        // Not yet implemented
        return null;
    }

    // Not yet implemented
}
