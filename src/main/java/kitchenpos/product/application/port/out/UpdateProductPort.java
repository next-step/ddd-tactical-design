package kitchenpos.product.application.port.out;

import kitchenpos.product.domain.Product;

public interface UpdateProductPort {
    Product updateProduct(final Product product);
}
