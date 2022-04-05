package kitchenpos.eatinorders.tobe.ordertable.infra;

import kitchenpos.eatinorders.tobe.ordertable.domain.OrderTable;
import kitchenpos.eatinorders.tobe.ordertable.domain.OrderTableRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaOrderTableRepository extends OrderTableRepository, JpaRepository<OrderTable, UUID> {
}
