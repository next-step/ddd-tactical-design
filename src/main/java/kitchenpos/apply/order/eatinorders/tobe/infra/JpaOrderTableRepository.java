package kitchenpos.apply.order.eatinorders.tobe.infra;

import kitchenpos.apply.order.eatinorders.tobe.domain.OrderTableRepository;
import kitchenpos.apply.order.eatinorders.tobe.domain.OrderTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaOrderTableRepository extends OrderTableRepository, JpaRepository<OrderTable, UUID> {
}
