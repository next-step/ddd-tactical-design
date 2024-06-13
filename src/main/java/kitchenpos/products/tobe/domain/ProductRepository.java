package kitchenpos.products.tobe.domain;

import kitchenpos.products.domain.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {
    Product save(kitchenpos.products.domain.Product product);

    Optional<Product> findById(UUID id);

    List<Product> findAll();

    List<Product> findAllByIdIn(List<UUID> ids);
}

