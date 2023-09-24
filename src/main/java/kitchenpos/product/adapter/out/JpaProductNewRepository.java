package kitchenpos.product.adapter.out;

import java.util.UUID;
import kitchenpos.product.application.port.out.ProductNewRepository;
import kitchenpos.product.domain.ProductNew;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProductNewRepository extends ProductNewRepository,
    JpaRepository<ProductNew, UUID> {

}
