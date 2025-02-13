package kitchenpos.order.infrastructure.persistence;

import java.util.UUID;
import kitchenpos.order.domain.entity.Order;
import kitchenpos.order.domain.repository.OrderRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOrderRepository extends OrderRepository, JpaRepository<Order, UUID> {

}
