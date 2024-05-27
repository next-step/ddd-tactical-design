package kitchenpos.domain.product.tobe.infra;

import kitchenpos.domain.product.tobe.domain.Product;
import kitchenpos.domain.product.tobe.domain.ProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaProductRepository extends ProductRepository, JpaRepository<Product, UUID> {
}
