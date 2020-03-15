package kitchenpos.products.tobe.domain;

import kitchenpos.products.model.ProductData;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Product save(Product entity);

    Optional<Product> findById(Long id);

    List<Product> findAll();
}
