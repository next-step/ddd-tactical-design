package kitchenpos.products.tobe.infra.repository;

import kitchenpos.products.tobe.domain.entity.Product;
import kitchenpos.products.tobe.domain.repository.ProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface JpaProductRepository extends ProductRepository, JpaRepository<Product, UUID> {
}
