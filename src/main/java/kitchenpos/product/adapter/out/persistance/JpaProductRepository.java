package kitchenpos.product.adapter.out.persistance;

import kitchenpos.product.application.port.out.ProductRepository;
import kitchenpos.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaProductRepository extends ProductRepository, JpaRepository<Product, UUID> {
}
