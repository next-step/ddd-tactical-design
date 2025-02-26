package kitchenpos.product.infrastructure.persistence;

import kitchenpos.product.domain.entity.Product;
import kitchenpos.product.domain.model.ProductId;
import kitchenpos.product.domain.repository.ProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProductRepository extends ProductRepository, JpaRepository<Product, ProductId> {

}
