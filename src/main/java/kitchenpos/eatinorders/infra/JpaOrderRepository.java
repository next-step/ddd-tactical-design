package kitchenpos.eatinorders.infra;

import kitchenpos.eatinorders.domain.eatinorder.Order;
import kitchenpos.eatinorders.domain.eatinorder.OrderRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaOrderRepository extends OrderRepository, JpaRepository<Order, UUID> {
}
