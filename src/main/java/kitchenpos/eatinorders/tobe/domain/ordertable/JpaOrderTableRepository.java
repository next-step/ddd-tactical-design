package kitchenpos.eatinorders.tobe.domain.ordertable;

import kitchenpos.common.domain.OrderTableId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOrderTableRepository extends OrderTableRepository, JpaRepository<OrderTable, OrderTableId> {
}
