package kitchenpos.products.tobe.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

interface ProductRepository {
    Product save(Product product);

    Optional<Product> findById(UUID id);

    List<Product> findAll();

    List<Product> findAllByIdIn(List<UUID> ids);
}

