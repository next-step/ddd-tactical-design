package kitchenpos.eatinorders.tobe.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository("TobeJpaOrderRepository")
public interface JpaOrderRepository extends OrderRepository, JpaRepository<Order, UUID> {
}
