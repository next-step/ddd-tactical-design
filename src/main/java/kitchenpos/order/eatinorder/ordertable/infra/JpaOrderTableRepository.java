package kitchenpos.order.eatinorder.ordertable.infra;

import java.util.UUID;
import kitchenpos.order.eatinorder.ordertable.domain.OrderTable;
import kitchenpos.order.eatinorder.ordertable.domain.OrderTableRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOrderTableRepository extends OrderTableRepository, JpaRepository<OrderTable, UUID> {

}
