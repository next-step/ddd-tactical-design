package kitchenpos.orders.tobe.infra;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import kitchenpos.orders.tobe.domain.Order;
import kitchenpos.orders.tobe.domain.OrderRepository;

public interface JpaOrderRepository extends OrderRepository, JpaRepository<Order, UUID> {
}
