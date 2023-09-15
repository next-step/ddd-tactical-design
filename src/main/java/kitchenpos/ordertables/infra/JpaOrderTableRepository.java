package kitchenpos.ordertables.infra;

import kitchenpos.ordertables.domain.OrderTable;
import kitchenpos.ordertables.domain.OrderTableId;
import kitchenpos.ordertables.domain.OrderTableRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaOrderTableRepository extends OrderTableRepository, JpaRepository<OrderTable, OrderTableId> {
}
