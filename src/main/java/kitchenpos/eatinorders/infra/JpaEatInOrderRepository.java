package kitchenpos.eatinorders.infra;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import kitchenpos.eatinorders.domain.order.EatInOrder;
import kitchenpos.eatinorders.domain.order.EatInOrderRepository;

public interface JpaEatInOrderRepository extends EatInOrderRepository, JpaRepository<EatInOrder, UUID> {
}
