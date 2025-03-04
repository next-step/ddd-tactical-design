package kitchenpos.order.common.infrastructure.persistence;

import kitchenpos.order.common.domain.entity.Order;
import kitchenpos.order.common.domain.model.OrderId;
import kitchenpos.order.common.domain.repository.OrderRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOrderRepository extends OrderRepository, JpaRepository<Order, OrderId> {

}
