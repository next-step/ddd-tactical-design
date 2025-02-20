package kitchenpos.order.common.infra.persistence;

import kitchenpos.order.common.model.Order;
import kitchenpos.order.common.repository.OrderRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaOrderRepository extends OrderRepository, JpaRepository<Order, UUID> {
}
