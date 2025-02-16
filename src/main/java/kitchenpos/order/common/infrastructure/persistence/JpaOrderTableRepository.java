package kitchenpos.order.common.infrastructure.persistence;

import java.util.UUID;
import kitchenpos.order.common.domain.entity.OrderTable;
import kitchenpos.order.common.domain.repository.OrderTableRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOrderTableRepository extends OrderTableRepository,
    JpaRepository<OrderTable, UUID> {

}
