package kitchenpos.products.tobe.infra.repository;

import java.util.UUID;
import kitchenpos.products.tobe.domain.entity.Product;
import kitchenpos.products.tobe.domain.repository.ProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;

interface JpaProductRepository extends ProductRepository, JpaRepository<Product, UUID> {
}
