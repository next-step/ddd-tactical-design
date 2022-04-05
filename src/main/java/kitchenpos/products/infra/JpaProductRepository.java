package kitchenpos.products.infra;

import kitchenpos.products.domain.Product;
import kitchenpos.products.domain.tobe.ProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaProductRepository extends ProductRepository, JpaRepository<Product, UUID> {
}
