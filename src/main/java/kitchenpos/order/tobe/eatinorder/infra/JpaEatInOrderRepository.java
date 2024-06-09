package kitchenpos.order.tobe.eatinorder.infra;

import kitchenpos.order.tobe.eatinorder.domain.EatInOrder;
import kitchenpos.order.tobe.eatinorder.domain.EatInOrderRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaEatInOrderRepository extends EatInOrderRepository, JpaRepository<EatInOrder, UUID> {
}
