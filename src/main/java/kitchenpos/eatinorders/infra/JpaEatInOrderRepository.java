package kitchenpos.eatinorders.infra;

import kitchenpos.eatinorders.todo.domain.orders.EatInOrder;
import kitchenpos.eatinorders.todo.domain.orders.EatInOrderRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaEatInOrderRepository extends EatInOrderRepository, JpaRepository<EatInOrder, UUID> {
}
