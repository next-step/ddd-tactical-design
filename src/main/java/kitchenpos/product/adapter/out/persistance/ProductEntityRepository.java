package kitchenpos.product.adapter.out.persistance;

import kitchenpos.product.adapter.out.persistance.entity.ProductEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductEntityRepository {
    ProductEntity save(ProductEntity product);

    Optional<ProductEntity> findById(UUID id);

    List<ProductEntity> findAll();

    List<ProductEntity> findAllByIdIn(List<UUID> ids);
}

