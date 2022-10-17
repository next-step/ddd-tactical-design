package kitchenpos.eatinorders.tobe.infra;

import kitchenpos.eatinorders.tobe.domain.model.EatInOrder;
import kitchenpos.eatinorders.tobe.domain.repository.EatInOrderRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaEatInOrderRepository extends JpaRepository<EatInOrder, UUID>, EatInOrderRepository {
}
