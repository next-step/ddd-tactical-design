package kitchenpos.orders.common.infra;

import java.util.UUID;
import kitchenpos.orders.common.domain.Order;
import kitchenpos.orders.common.domain.OrderRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOrderRepository extends OrderRepository, JpaRepository<Order, UUID> {

}
