package kitchenpos.eatinorders.ordertable.infra;

import kitchenpos.eatinorders.ordertable.tobe.domain.OrderTable;
import kitchenpos.eatinorders.ordertable.tobe.domain.OrderTableRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaOrderTableRepository extends OrderTableRepository, JpaRepository<OrderTable, UUID> {
}
