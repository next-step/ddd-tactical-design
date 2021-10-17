package kitchenpos.eatinorders.tobe.domain.repository;

import java.util.UUID;
import kitchenpos.eatinorders.tobe.domain.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("tobeJpaOrderRepository")
public interface JpaOrderRepository extends OrderRepository, JpaRepository<Order, UUID> {
}
