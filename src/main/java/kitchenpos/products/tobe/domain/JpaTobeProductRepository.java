package kitchenpos.products.tobe.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaTobeProductRepository extends TobeProductRepository, JpaRepository<Product, UUID> {
}
