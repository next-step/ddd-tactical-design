package kitchenpos.order.tobe.eatinorder.infra;

import java.util.UUID;
import kitchenpos.order.tobe.eatinorder.domain.OrderTable;
import kitchenpos.order.tobe.eatinorder.domain.OrderTableRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOrderTableRepository extends OrderTableRepository, JpaRepository<OrderTable, UUID> {

}
