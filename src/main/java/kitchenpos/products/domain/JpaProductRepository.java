package kitchenpos.products.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

@Deprecated(forRemoval = true)
public interface JpaProductRepository extends ProductRepository, JpaRepository<Product, UUID> {
}
