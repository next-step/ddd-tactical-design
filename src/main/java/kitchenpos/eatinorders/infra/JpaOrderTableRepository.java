package kitchenpos.eatinorders.infra;

import java.util.UUID;
import kitchenpos.eatinorders.domain.ordertable.OrderTable;
import kitchenpos.eatinorders.domain.ordertable.OrderTableRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOrderTableRepository extends OrderTableRepository, JpaRepository<OrderTable, UUID> {
}
