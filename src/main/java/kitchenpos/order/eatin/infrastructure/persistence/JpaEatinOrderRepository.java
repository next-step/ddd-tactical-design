package kitchenpos.order.eatin.infrastructure.persistence;

import kitchenpos.order.common.domain.model.OrderId;
import kitchenpos.order.eatin.domain.model.EatInOrder;
import kitchenpos.order.eatin.domain.repository.EatinOrderRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaEatinOrderRepository extends EatinOrderRepository, JpaRepository<EatInOrder, OrderId> {

}
