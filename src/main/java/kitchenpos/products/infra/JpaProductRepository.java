package kitchenpos.products.infra;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

import kitchenpos.products.domain.Product;
import kitchenpos.products.domain.ProductRepository;

public interface JpaProductRepository extends ProductRepository, JpaRepository<Product, UUID> {
}
