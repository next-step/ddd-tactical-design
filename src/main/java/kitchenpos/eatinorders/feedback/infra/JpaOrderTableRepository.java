package kitchenpos.eatinorders.feedback.infra;

import kitchenpos.eatinorders.feedback.domain.OrderTable;
import kitchenpos.eatinorders.feedback.domain.OrderTableRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOrderTableRepository extends OrderTableRepository, JpaRepository<OrderTable, Long> {
}
