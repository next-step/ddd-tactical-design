package kitchenpos.orders.common.infra;

import kitchenpos.orders.common.domain.Order;
import kitchenpos.orders.common.domain.OrderRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaOrderRepository extends OrderRepository, JpaRepository<Order, UUID> {
}
