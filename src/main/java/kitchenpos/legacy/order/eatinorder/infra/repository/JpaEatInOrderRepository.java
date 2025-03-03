package kitchenpos.legacy.order.eatinorder.infra.repository;

import kitchenpos.legacy.order.eatinorder.domain.model.EatInOrder;
import kitchenpos.legacy.order.eatinorder.domain.repository.EatInOrderRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaEatInOrderRepository extends EatInOrderRepository, JpaRepository<EatInOrder, UUID> {
}
