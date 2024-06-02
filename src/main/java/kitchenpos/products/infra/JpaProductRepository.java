package kitchenpos.products.infra;

import java.util.UUID;

import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProductRepository extends ProductRepository, JpaRepository<Product, UUID> {
}
