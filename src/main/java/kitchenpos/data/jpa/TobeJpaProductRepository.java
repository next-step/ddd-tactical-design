package kitchenpos.data.jpa;

import kitchenpos.core.products.tobe.domain.Product;
import kitchenpos.core.shared.identifier.ProductId;
import kitchenpos.core.products.tobe.domain.TobeProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;

interface TobeJpaProductRepository extends TobeProductRepository, JpaRepository<Product, ProductId> {
}
