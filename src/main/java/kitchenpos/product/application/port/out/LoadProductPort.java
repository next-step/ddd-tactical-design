package kitchenpos.product.application.port.out;

import kitchenpos.product.domain.Product;
import kitchenpos.product.domain.ProductId;

import java.util.Optional;

public interface LoadProductPort {
    Optional<Product> loadProductById(final ProductId id);
}
