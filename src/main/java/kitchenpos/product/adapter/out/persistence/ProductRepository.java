package kitchenpos.product.adapter.out.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

// TODO: public 접근 제어자 제거
public interface ProductRepository {
    ProductEntity save(ProductEntity product);

    Optional<ProductEntity> findById(UUID id);

    List<ProductEntity> findAll();

    List<ProductEntity> findAllByIdIn(List<UUID> ids);
}

