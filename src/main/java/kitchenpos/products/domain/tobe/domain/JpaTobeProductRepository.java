package kitchenpos.products.domain.tobe.domain;

import kitchenpos.products.domain.Product;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.domain.tobe.domain.vo.ProductId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaTobeProductRepository extends TobeProductRepository, JpaRepository<TobeProduct, ProductId> {
}
