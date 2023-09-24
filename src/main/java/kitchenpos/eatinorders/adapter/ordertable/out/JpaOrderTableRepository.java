package kitchenpos.eatinorders.adapter.ordertable.out;

import java.util.UUID;
import kitchenpos.eatinorders.application.ordertable.port.out.OrderTableNewRepository;
import kitchenpos.eatinorders.domain.ordertable.OrderTableNew;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOrderTableRepository extends OrderTableNewRepository,
    JpaRepository<OrderTableNew, UUID> {

}
