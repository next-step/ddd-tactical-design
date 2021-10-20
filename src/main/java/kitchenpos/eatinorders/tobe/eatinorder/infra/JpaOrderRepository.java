package kitchenpos.eatinorders.tobe.eatinorder.infra;

import kitchenpos.eatinorders.tobe.eatinorder.domain.EatInOrder;
import kitchenpos.eatinorders.tobe.eatinorder.domain.OrderRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaOrderRepository extends OrderRepository, JpaRepository<EatInOrder, UUID> {
}
