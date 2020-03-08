package kitchenpos.products.tobe.domain;

import java.util.List;

public interface ProductDao {
    Product save(final Product product);
    List<Product> findAll();
}
