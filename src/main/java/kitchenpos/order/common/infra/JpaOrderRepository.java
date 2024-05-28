package kitchenpos.order.common.infra;

import kitchenpos.order.common.domain.Order;
import kitchenpos.order.common.domain.OrderRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaOrderRepository extends OrderRepository, JpaRepository<Order, UUID> {
}
