package kitchenpos.products.domain;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("tobeProductRepository")
public interface JpaProductRepository extends ProductRepository, JpaRepository<Product, UUID> {
}
