package kitchenpos.products.tobe.infra;

import java.util.UUID;
import kitchenpos.products.tobe.domain.model.Product;
import kitchenpos.products.tobe.domain.repository.ProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("tobeProductRepository")
public interface JpaProductRepository extends ProductRepository, JpaRepository<Product, UUID> {
}
