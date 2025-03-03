package kitchenpos.order.common.infrastructure.persistence;

import kitchenpos.order.common.domain.repository.OrderTableRepository;
import kitchenpos.order.eatin.domain.entity.OrderTable;
import kitchenpos.order.eatin.domain.model.OrderTableId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOrderTableRepository extends OrderTableRepository,
    JpaRepository<OrderTable, OrderTableId> {

}
