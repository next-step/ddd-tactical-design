package kitchenpos.products.domain.tobe.domain;

import kitchenpos.products.domain.tobe.domain.vo.ProductId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTobeProductRepository extends TobeProductRepository, JpaRepository<TobeProduct, ProductId> {
}
