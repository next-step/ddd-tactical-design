package kitchenpos.eatinorders.infra;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import kitchenpos.eatinorders.domain.Order;
import kitchenpos.eatinorders.domain.OrderRepository;

public interface JpaOrderRepository extends OrderRepository, JpaRepository<Order, UUID> {
}
