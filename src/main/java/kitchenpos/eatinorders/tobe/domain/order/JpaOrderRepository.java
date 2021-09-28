package kitchenpos.eatinorders.tobe.domain.order;

import kitchenpos.common.domain.OrderId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOrderRepository extends OrderRepository, JpaRepository<Order, OrderId> {

}
