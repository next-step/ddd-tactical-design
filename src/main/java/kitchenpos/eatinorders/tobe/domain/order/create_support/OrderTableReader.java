package kitchenpos.eatinorders.tobe.domain.order.create_support;

import java.util.Optional;
import java.util.UUID;

public interface OrderTableReader {

  Optional<OrderTable> getById(UUID orderTableId);
}
