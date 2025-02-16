package kitchenpos.product.adapter.out.persistance;

import kitchenpos.product.adapter.out.persistance.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaProductEntityRepository extends ProductEntityRepository, JpaRepository<ProductEntity, UUID> {
}
