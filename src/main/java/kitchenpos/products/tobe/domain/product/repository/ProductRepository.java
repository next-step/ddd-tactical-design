package kitchenpos.products.tobe.domain.product.repository;

import kitchenpos.products.tobe.domain.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
