package kitchenpos.products.tobe.domain;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProductRepository extends ProductRepository, JpaRepository<Product, UUID> {
}
