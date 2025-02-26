package kitchenpos.products.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaProductRepository_asis extends ProductRepository, JpaRepository<ProductAsis, UUID> {
}
