package kitchenpos.eatinorders.tobe.infra;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import kitchenpos.eatinorders.tobe.domain.Order;
import kitchenpos.eatinorders.tobe.domain.OrderRepository;

public interface JpaOrderRepository extends OrderRepository, JpaRepository<Order, UUID> {
}
