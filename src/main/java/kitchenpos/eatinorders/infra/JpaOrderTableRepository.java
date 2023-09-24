package kitchenpos.eatinorders.infra;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import kitchenpos.eatinorders.domain.ordertable.OrderTable;
import kitchenpos.eatinorders.domain.ordertable.OrderTableRepository;

public interface JpaOrderTableRepository extends OrderTableRepository, JpaRepository<OrderTable, UUID> {
}
