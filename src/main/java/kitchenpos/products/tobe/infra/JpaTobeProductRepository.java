package kitchenpos.products.tobe.infra;

import kitchenpos.products.tobe.domain.TobeProduct;
import kitchenpos.products.tobe.domain.TobeProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaTobeProductRepository extends TobeProductRepository, JpaRepository<TobeProduct, UUID> {
}
