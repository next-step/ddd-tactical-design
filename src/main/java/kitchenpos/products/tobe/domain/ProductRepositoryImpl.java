package kitchenpos.products.tobe.domain;

import kitchenpos.products.tobe.domain.entity.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepositoryImpl {
    public Product save(final Product product);

    Optional<Product> findById(UUID productId);

    void update(Product changedProduct);

    List<Product> findAll();
}
