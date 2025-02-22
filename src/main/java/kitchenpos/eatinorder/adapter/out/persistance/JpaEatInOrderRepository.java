package kitchenpos.eatinorder.adapter.out.persistance;

import kitchenpos.deliveryorder.application.port.out.OrderRepository;
import kitchenpos.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaEatInOrderRepository extends OrderRepository, JpaRepository<Order, UUID> {
}
