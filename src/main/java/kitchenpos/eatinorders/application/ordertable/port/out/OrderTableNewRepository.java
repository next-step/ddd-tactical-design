package kitchenpos.eatinorders.application.ordertable.port.out;

import java.util.Optional;
import java.util.UUID;
import kitchenpos.eatinorders.domain.ordertable.OrderTableNew;

public interface OrderTableNewRepository {

    OrderTableNew save(final OrderTableNew orderTable);

    Optional<OrderTableNew> findById(final UUID id);
}
