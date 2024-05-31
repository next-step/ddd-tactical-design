package kitchenpos.products.tobe.domain;

import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {
  Product save(Product product);
  Optional<Product> findById(UUID id);
}
