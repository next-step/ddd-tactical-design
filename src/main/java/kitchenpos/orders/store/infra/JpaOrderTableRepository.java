package kitchenpos.orders.store.infra;

import java.util.UUID;
import kitchenpos.orders.store.domain.OrderTableRepository;
import kitchenpos.orders.store.domain.tobe.OrderTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOrderTableRepository extends OrderTableRepository,
        JpaRepository<OrderTable, UUID> {

}
