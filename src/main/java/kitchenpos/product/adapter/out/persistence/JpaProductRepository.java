package kitchenpos.product.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface JpaProductRepository extends ProductRepository, JpaRepository<ProductEntity, UUID> {
}
