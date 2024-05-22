package kitchenpos.products.domain;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

@Primary
public interface LegacyJpaProductRepository extends ProductRepository, JpaRepository<Product, UUID> {
}
