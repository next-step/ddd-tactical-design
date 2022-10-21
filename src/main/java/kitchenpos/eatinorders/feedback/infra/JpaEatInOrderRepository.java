package kitchenpos.eatinorders.feedback.infra;

import kitchenpos.eatinorders.feedback.domain.EatInOrder;
import kitchenpos.eatinorders.feedback.domain.EatInOrderRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaEatInOrderRepository extends EatInOrderRepository, JpaRepository<EatInOrder, Long> {
}
