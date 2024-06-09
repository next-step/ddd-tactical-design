package kitchenpos.eatinorders.tobe.domain.fake;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import kitchenpos.eatinorders.tobe.domain.order.create_support.OrderTable;
import kitchenpos.eatinorders.tobe.domain.order.create_support.OrderTableReader;

public class FakeOrderTableReader implements OrderTableReader {

  private final Map<UUID, OrderTable> orderTableMap = new HashMap<>();

  public FakeOrderTableReader(List<OrderTable> orderTableList) {
    for (OrderTable orderTable : orderTableList) {
      this.orderTableMap.put(orderTable.getId(), orderTable);
    }
  }

  @Override
  public Optional<OrderTable> getById(UUID orderTableId) {
    if (orderTableMap.containsKey(orderTableId)) {
      return Optional.of(orderTableMap.get(orderTableId));
    }
    return Optional.empty();
  }
}
