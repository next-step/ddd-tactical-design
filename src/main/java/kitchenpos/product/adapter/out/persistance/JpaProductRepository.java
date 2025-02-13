package kitchenpos.product.adapter.out.persistance;

import kitchenpos.product.domain.model.Product;
import kitchenpos.product.application.port.out.ProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaProductRepository extends ProductRepository, JpaRepository<Product, UUID> {
}
