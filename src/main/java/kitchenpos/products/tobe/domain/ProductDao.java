package kitchenpos.products.tobe.domain;

import kitchenpos.products.tobe.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductDao {
    Product save(Product entity);

    Optional<Product> findById(Long id);

    List<Product> findAll();
}
