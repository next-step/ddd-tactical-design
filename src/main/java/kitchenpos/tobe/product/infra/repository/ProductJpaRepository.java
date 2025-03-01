package kitchenpos.tobe.product.infra.repository;

import kitchenpos.tobe.product.domain.Product;
import kitchenpos.tobe.product.domain.ProductId;
import kitchenpos.tobe.product.domain.ProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends ProductRepository, JpaRepository<Product, ProductId> {
}
