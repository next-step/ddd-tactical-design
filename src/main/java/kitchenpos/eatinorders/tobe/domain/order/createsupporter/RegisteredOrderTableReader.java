package kitchenpos.eatinorders.tobe.domain.order.createsupporter;

import java.util.Optional;
import java.util.UUID;

public interface RegisteredOrderTableReader {

  Optional<RegisteredOrderTable> getById(UUID orderTableId);
}
