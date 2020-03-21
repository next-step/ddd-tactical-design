package kitchenpos.products.tobe.domain;

import java.util.List;

public interface ProductRepository {
    List<Product> findAll();

    void save(Product product);

    Product findById(Long id);
}
