package kitchenpos.product.adapter.out.persistence;

import kitchenpos.product.domain.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

// TODO: public 접근 제어자 제거
public interface ProductRepository {
    Product save(Product product);

    Optional<Product> findById(UUID id);

    List<Product> findAll();

    List<Product> findAllByIdIn(List<UUID> ids);
}

