package kitchenpos.products.infra;

import java.util.UUID;
import kitchenpos.products.domain.tobe.Product;
import kitchenpos.products.domain.tobe.ProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProductRepository extends ProductRepository, JpaRepository<Product, UUID> {

}
