package kitchenpos.products.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaLegacyProductRepository extends LegacyProductRepository, JpaRepository<LegacyProduct, UUID> {
}
