package kitchenpos.eatinorders.infra;

import kitchenpos.eatinorders.domain.order.EatInOrder;
import kitchenpos.eatinorders.domain.order.EatInOrderId;
import kitchenpos.eatinorders.domain.order.EatInOrderRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaEatInOrderRepository extends EatInOrderRepository, JpaRepository<EatInOrder, EatInOrderId> {
}
