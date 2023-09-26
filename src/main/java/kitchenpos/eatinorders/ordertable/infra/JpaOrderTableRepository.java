package kitchenpos.eatinorders.ordertable.infra;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import kitchenpos.eatinorders.ordertable.domain.OrderTable;
import kitchenpos.eatinorders.ordertable.domain.OrderTableRepository;

public interface JpaOrderTableRepository extends OrderTableRepository, JpaRepository<OrderTable, UUID> {
}
