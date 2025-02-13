package kitchenpos.order.infrastructure.persistence;

import java.util.UUID;
import kitchenpos.order.domain.entity.OrderTable;
import kitchenpos.order.domain.repository.OrderTableRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOrderTableRepository extends OrderTableRepository,
    JpaRepository<OrderTable, UUID> {

}
