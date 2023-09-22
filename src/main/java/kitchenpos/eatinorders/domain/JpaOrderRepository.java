package kitchenpos.eatinorders.domain;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOrderRepository extends OrderRepository, JpaRepository<Order, UUID> {
}
