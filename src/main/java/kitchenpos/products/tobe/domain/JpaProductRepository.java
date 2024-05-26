package kitchenpos.products.tobe.domain;

import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface JpaProductRepository extends ProductRepository,
    CrudRepository<Product, UUID> {

}
