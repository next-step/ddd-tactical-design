package kitchenpos.products.tobe.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import kitchenpos.products.tobe.domain.Product;

import java.util.UUID;

public interface JpaProductRepository extends ProductRepository, JpaRepository<Product, UUID> {
}
