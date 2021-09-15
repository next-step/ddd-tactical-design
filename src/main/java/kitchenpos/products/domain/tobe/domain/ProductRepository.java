package kitchenpos.products.domain.tobe.domain;

import java.util.List;
import java.util.Optional;
import kitchenpos.common.domain.ProductId;

public interface ProductRepository {
    Product save(Product product);

    Optional<Product> findById(ProductId id);

    List<Product> findAll();

    List<Product> findAllByIdIn(List<ProductId> ids);
}
