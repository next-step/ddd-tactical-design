package kitchenpos.eatinorder.adapter.out.persistance;

import kitchenpos.eatinorder.domain.model.Order;
import kitchenpos.eatinorder.application.port.out.OrderRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaOrderRepository extends OrderRepository, JpaRepository<Order, UUID> {
}
