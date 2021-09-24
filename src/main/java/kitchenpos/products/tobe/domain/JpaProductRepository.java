package kitchenpos.products.tobe.domain;

import kitchenpos.common.domain.ProductId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProductRepository extends ProductRepository, JpaRepository<Product, ProductId> {

}
