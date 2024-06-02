package kitchenpos.products.tobe.repository;

import java.util.UUID;

import kitchenpos.products.tobe.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProductRepository extends ProductRepository, JpaRepository<Product, UUID> {
}
