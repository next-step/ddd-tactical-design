package kitchenpos.products.tobe.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import kitchenpos.products.tobe.domain.model.entity.Product;

public interface ProductRepository {
    Product save(Product product);

    Optional<Product> findById(UUID id);

    Product getById(UUID id);

    List<Product> findAll();

}
