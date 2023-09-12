package kitchenpos.product.application.port.out;

import kitchenpos.product.domain.Product;

public interface UpdateProductPort {
    void updateProduct(final Product product);
}
