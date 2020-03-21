package kitchenpos.products.tobe.infra;

import kitchenpos.products.tobe.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository{
    Product save (Product product);
    Optional<Product> findById (Long id);
    List<Product> list ();
    boolean findByName (String name);
}
