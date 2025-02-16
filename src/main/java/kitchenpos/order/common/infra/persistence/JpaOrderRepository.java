package kitchenpos.order.common.infra.persistence;

import java.util.UUID;
import kitchenpos.order.common.model.Order;
import kitchenpos.order.common.repository.OrderRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOrderRepository extends OrderRepository, JpaRepository<Order, UUID> {
}
