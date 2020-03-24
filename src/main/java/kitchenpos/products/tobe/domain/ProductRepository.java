package kitchenpos.products.tobe.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Geonguk Han
 * @since 2020-03-07
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
