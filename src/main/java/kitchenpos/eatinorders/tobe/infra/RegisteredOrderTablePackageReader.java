package kitchenpos.eatinorders.tobe.infra;

import java.util.Optional;
import java.util.UUID;
import kitchenpos.eatinorders.domain.OrderTableRepository;
import kitchenpos.eatinorders.tobe.domain.order.createsupporter.RegisteredOrderTable;
import kitchenpos.eatinorders.tobe.domain.order.createsupporter.RegisteredOrderTableReader;
import org.springframework.stereotype.Component;

@Component
public class RegisteredOrderTablePackageReader implements RegisteredOrderTableReader {
  private final OrderTableRepository orderTableRepository;

  public RegisteredOrderTablePackageReader(OrderTableRepository orderTableRepository) {
    this.orderTableRepository = orderTableRepository;
  }

  @Override
  public Optional<RegisteredOrderTable> getById(UUID orderTableId) {
    Optional<kitchenpos.eatinorders.domain.OrderTable> orderTableOptional = orderTableRepository.findById(
        orderTableId);
    if(orderTableOptional.isEmpty()) {
      return Optional.empty();
    }
    kitchenpos.eatinorders.domain.OrderTable orderTable = orderTableOptional.get();
    return Optional.of(new RegisteredOrderTable(orderTable.getId(), orderTable.isOccupied()));
  }
}
