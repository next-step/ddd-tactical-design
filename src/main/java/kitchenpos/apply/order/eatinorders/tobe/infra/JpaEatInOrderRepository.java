package kitchenpos.apply.order.eatinorders.tobe.infra;


import kitchenpos.apply.order.eatinorders.tobe.domain.EatInOrder;
import kitchenpos.apply.order.eatinorders.tobe.domain.EatInOrderRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaEatInOrderRepository extends EatInOrderRepository, JpaRepository<EatInOrder, UUID> {
}
