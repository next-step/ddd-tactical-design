package kitchenpos.products.tobe.domain.repository;

import kitchenpos.products.tobe.domain.model.Product;

import java.util.List;
import java.util.UUID;

public interface ProductRepository {

    Product save(Product product);

    List<Product> findAllByIdIn(List<UUID> ids);
}
