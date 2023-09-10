package kitchenpos.products.tobe.infra;


import kitchenpos.products.tobe.domain.TobeProduct;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TobeJpaProductRepository extends ProductRepository, JpaRepository<TobeProduct, UUID> {
}
