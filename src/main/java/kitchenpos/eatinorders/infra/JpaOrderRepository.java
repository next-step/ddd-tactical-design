package kitchenpos.eatinorders.infra;

import java.util.UUID;
import kitchenpos.eatinorders.domain.eatinorder.Order;
import kitchenpos.eatinorders.domain.eatinorder.OrderRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOrderRepository extends OrderRepository, JpaRepository<Order, UUID> {
}
