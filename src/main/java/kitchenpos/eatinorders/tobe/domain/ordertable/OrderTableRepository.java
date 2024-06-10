package kitchenpos.eatinorders.tobe.domain.ordertable;

import java.util.Optional;
import java.util.UUID;

public interface OrderTableRepository {
  Optional<OrderTable> findById(UUID id);
}
