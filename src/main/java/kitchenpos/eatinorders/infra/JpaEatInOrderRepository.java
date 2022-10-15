package kitchenpos.eatinorders.infra;

import java.util.UUID;
import kitchenpos.eatinorders.domain.EatInOrder;
import kitchenpos.eatinorders.domain.EatInOrderRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaEatInOrderRepository extends EatInOrderRepository, JpaRepository<EatInOrder, UUID> {
}
