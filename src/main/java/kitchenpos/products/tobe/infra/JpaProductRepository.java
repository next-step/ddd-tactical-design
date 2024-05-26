package kitchenpos.products.tobe.infra;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;

public interface JpaProductRepository extends ProductRepository, JpaRepository<Product, UUID> {
}
