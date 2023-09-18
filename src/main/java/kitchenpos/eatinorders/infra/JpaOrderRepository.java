package kitchenpos.eatinorders.infra;

import kitchenpos.eatinorders.domain.tobe.EatInOrder;
import kitchenpos.eatinorders.domain.tobe.OrderRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaOrderRepository extends OrderRepository, JpaRepository<EatInOrder, UUID> {
}
