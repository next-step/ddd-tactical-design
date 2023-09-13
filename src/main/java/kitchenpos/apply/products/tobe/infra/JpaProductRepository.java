package kitchenpos.apply.products.tobe.infra;

import kitchenpos.apply.products.tobe.domain.ProductRepository;
import kitchenpos.apply.products.tobe.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaProductRepository extends ProductRepository, JpaRepository<Product, UUID> {
}
