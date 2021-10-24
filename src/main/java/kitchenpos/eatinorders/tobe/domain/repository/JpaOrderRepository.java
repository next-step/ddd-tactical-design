package kitchenpos.eatinorders.tobe.domain.repository;

import kitchenpos.eatinorders.tobe.domain.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaOrderRepository extends JpaRepository<Order, UUID>, OrderRepository {
}
