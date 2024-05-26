package kitchenpos.products.infra;

import kitchenpos.products.tobe.domain.ProductRepository;
import kitchenpos.products.tobe.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaProductRepository extends ProductRepository, JpaRepository<Product, UUID> {
}
