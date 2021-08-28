package kitchenpos.products.tobe.domain;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    List<Product> findAll();

    void save(Product product);

    Optional<Product> findById(ProductId id);
}
