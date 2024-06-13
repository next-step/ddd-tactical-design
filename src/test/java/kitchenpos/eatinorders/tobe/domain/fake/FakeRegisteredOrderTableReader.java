package kitchenpos.eatinorders.tobe.domain.fake;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import kitchenpos.eatinorders.tobe.domain.order.createsupporter.RegisteredOrderTable;
import kitchenpos.eatinorders.tobe.domain.order.createsupporter.RegisteredOrderTableReader;

public class FakeRegisteredOrderTableReader implements RegisteredOrderTableReader {

  private final Map<UUID, RegisteredOrderTable> orderTableMap = new HashMap<>();

  public FakeRegisteredOrderTableReader(List<RegisteredOrderTable> registeredOrderTableList) {
    for (RegisteredOrderTable registeredOrderTable : registeredOrderTableList) {
      this.orderTableMap.put(registeredOrderTable.getId(), registeredOrderTable);
    }
  }

  @Override
  public Optional<RegisteredOrderTable> getById(UUID orderTableId) {
    if (orderTableMap.containsKey(orderTableId)) {
      return Optional.of(orderTableMap.get(orderTableId));
    }
    return Optional.empty();
  }
}
