package kitchenpos.products.domain;

import java.util.UUID;
import kitchenpos.products.tobe.domain.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProductRepository extends ProductRepository, JpaRepository<Product, UUID> {

}
