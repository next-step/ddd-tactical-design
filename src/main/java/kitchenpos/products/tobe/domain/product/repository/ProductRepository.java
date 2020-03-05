package kitchenpos.products.tobe.domain.product.repository;

import kitchenpos.products.tobe.domain.product.domain.ProductDomain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductDomain, Long> {
}
