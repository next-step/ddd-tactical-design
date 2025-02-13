package kitchenpos.menu.infrastructure.persistence;

import java.util.UUID;
import kitchenpos.menu.domain.entity.Product;
import kitchenpos.menu.domain.repository.ProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProductRepository extends ProductRepository, JpaRepository<Product, UUID> {

}
