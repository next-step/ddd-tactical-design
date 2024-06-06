package kitchenpos.orders.eatin.infra;

import java.util.UUID;
import kitchenpos.orders.eatin.domain.OrderTable;
import kitchenpos.orders.eatin.domain.OrderTableRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOrderTableRepository extends OrderTableRepository,
        JpaRepository<OrderTable, UUID> {

}
