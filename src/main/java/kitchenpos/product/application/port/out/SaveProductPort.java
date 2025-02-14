package kitchenpos.product.application.port.out;

import kitchenpos.product.domain.model.Product;

public interface SaveProductPort {
    Product save(Product product);
}
