package kitchenpos.eatinorders.domain.tobe.domain;

import kitchenpos.eatinorders.domain.tobe.domain.vo.OrderId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaEatInOrderRepository extends EatInOrderRepository, JpaRepository<EatInOrder, OrderId> {
}
