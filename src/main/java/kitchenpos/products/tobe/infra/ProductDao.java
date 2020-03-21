package kitchenpos.products.tobe.infra;

import kitchenpos.products.tobe.domain.Product;

import java.util.List;

public interface ProductDao {
    Product save(final Product product);
    List<Product> findAll();
}
