package kitchenpos.product.tobe.infra;

import kitchenpos.product.tobe.domain.Product;
import kitchenpos.product.tobe.domain.ProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository("newProductRepository")
public interface JpaProductRepository extends ProductRepository, JpaRepository<Product, UUID> {
}
