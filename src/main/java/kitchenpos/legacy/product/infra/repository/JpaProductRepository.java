package kitchenpos.legacy.product.infra.repository;

import kitchenpos.legacy.product.domain.model.Product;
import kitchenpos.legacy.product.domain.repository.ProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaProductRepository extends ProductRepository, JpaRepository<Product, UUID> {
}
