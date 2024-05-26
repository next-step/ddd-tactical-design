package kitchenpos.domain.product.tobe.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProductRepository extends ProductRepository, JpaRepository<Product, ProductId> {
}
