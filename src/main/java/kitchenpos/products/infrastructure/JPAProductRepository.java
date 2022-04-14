package kitchenpos.products.infrastructure;

import java.util.UUID;
import kitchenpos.products.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JPAProductRepository extends JpaRepository<Product, UUID> {

}
