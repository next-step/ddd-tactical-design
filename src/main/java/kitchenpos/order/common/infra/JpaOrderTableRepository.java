package kitchenpos.order.common.infra;

import kitchenpos.order.eatin.domain.OrderTable;
import kitchenpos.order.eatin.domain.OrderTableRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaOrderTableRepository extends OrderTableRepository, JpaRepository<OrderTable, UUID> {
}
