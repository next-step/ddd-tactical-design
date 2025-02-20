package kitchenpos.product.infra.persistence;

import kitchenpos.product.domain.model.Product;
import kitchenpos.product.domain.repository.ProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaProductRepository extends ProductRepository, JpaRepository<Product, UUID> {
}
