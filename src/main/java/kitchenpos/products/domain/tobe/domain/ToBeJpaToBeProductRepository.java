package kitchenpos.products.domain.tobe.domain;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ToBeJpaToBeProductRepository extends ToBeProductRepository, JpaRepository<ToBeProduct, UUID> {
}
