package kitchenpos.eatinorders.infrastructure;

import kitchenpos.eatinorders.domain.orders.EatInOrder;
import kitchenpos.eatinorders.domain.orders.EatInOrderRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaEatInOrderRepository extends EatInOrderRepository, JpaRepository<EatInOrder, UUID> {
}
