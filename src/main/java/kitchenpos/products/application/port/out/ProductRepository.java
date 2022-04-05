package kitchenpos.products.application.port.out;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import kitchenpos.products.domain.Product;

public interface ProductRepository {
    Product save(Product product);

    Optional<Product> findById(UUID id);

    List<Product> findAll();

    List<Product> findAllByIdIn(List<UUID> ids);
}

