package kitchenpos.eatinorders.order.tobe.infra;

import kitchenpos.eatinorders.order.tobe.domain.EatInOrder;
import kitchenpos.eatinorders.order.tobe.domain.EatInOrderRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaEatInOrderRepository extends EatInOrderRepository, JpaRepository<EatInOrder, UUID> {
}
