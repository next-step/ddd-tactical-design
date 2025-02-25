package kitchenpos.products.infra;

import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductId;
import kitchenpos.products.tobe.domain.TobeProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TobeJpaProductRepository extends TobeProductRepository, JpaRepository<Product, ProductId> {
}
