package kitchenpos.eatinorders.domain;

import kitchenpos.eatinorders.tobe.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaOrderRepository extends OrderRepository, JpaRepository<Order, UUID> {
}
