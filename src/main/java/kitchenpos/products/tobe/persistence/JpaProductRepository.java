package kitchenpos.products.tobe.persistence;

import kitchenpos.products.tobe.persistence.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaProductRepository extends JpaRepository<ProductEntity, UUID> {
}
