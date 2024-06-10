package kitchenpos.eatinorders.tobe.infra;

import java.util.Optional;
import java.util.UUID;
import kitchenpos.eatinorders.domain.OrderTableRepository;
import kitchenpos.eatinorders.tobe.domain.order.createsupporter.OrderTable;
import kitchenpos.eatinorders.tobe.domain.order.createsupporter.OrderTableReader;
import org.springframework.stereotype.Component;

@Component
public class OrderTablePackageReader implements OrderTableReader {
  private final OrderTableRepository orderTableRepository;

  public OrderTablePackageReader(OrderTableRepository orderTableRepository) {
    this.orderTableRepository = orderTableRepository;
  }

  @Override
  public Optional<OrderTable> getById(UUID orderTableId) {
    Optional<kitchenpos.eatinorders.domain.OrderTable> orderTableOptional = orderTableRepository.findById(
        orderTableId);
    if(orderTableOptional.isEmpty()) {
      return Optional.empty();
    }
    kitchenpos.eatinorders.domain.OrderTable orderTable = orderTableOptional.get();
    return Optional.of(new OrderTable(orderTable.getId(), orderTable.isOccupied()));
  }
}
