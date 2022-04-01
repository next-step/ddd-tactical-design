package kitchenpos.products.tobe.domain;

import java.util.List;
import java.util.UUID;

public interface ProductRepository {

    Product save(Product product);

    Product findById(UUID id);

    List<Product> findAll();

    List<Product> findAllByIdIn(List<UUID> ids);
}
