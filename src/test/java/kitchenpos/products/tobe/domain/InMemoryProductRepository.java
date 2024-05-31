package kitchenpos.products.tobe.domain;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryProductRepository implements ProductRepository {
  private final Map<UUID, Product> maps = new ConcurrentHashMap<>();
  @Override
  public Product save(Product product) {
    maps.put(product.getId(), product);
    return product;
  }

  @Override
  public Optional<Product> findById(UUID id) {
    return Optional.ofNullable(maps.get(id));
  }
}
