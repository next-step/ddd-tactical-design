package kitchenpos.deliveryorder.adapter.out.persistance;

import kitchenpos.deliveryorder.application.port.out.OrderRepository;
import kitchenpos.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaDeliveryOrderRepository extends OrderRepository, JpaRepository<Order, UUID> {
}
