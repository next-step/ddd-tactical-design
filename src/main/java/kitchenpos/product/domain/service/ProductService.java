package kitchenpos.product.domain.service;

import java.util.List;
import java.util.UUID;
import kitchenpos.product.domain.entity.Product;

public interface ProductService {
    Product create(final Product request);
    Product changePrice(final UUID productId, final Product request);
    List<Product> findAll();
}
