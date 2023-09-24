package kitchenpos.eatinorders.adapter.eatinorder.out;

import java.util.UUID;
import kitchenpos.eatinorders.application.eatinorder.port.out.EatinOrderRepository;
import kitchenpos.eatinorders.domain.eatinorder.EatInOrder;
import kitchenpos.product.application.port.out.ProductNewRepository;
import kitchenpos.product.domain.ProductNew;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaEatinOrderRepository extends EatinOrderRepository, JpaRepository<EatInOrder, UUID> {
}
