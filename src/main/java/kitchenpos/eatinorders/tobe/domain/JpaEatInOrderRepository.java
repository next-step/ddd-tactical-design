package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.eatinorders.domain.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaEatInOrderRepository extends EatInOrderRepository, JpaRepository<EatInOrder, UUID> {
}
