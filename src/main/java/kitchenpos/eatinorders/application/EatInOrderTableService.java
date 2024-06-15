package kitchenpos.eatinorders.application;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;
import kitchenpos.eatinorders.domain.EatInOrderRepository;
import kitchenpos.eatinorders.domain.EatInOrderStatus;
import kitchenpos.eatinorders.domain.EatInOrderTable;
import kitchenpos.eatinorders.domain.EatInOrderTableRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EatInOrderTableService {
  private final EatInOrderTableRepository orderTableRepository;
  private final EatInOrderRepository orderRepository;

  public EatInOrderTableService(
      final EatInOrderTableRepository orderTableRepository,
      final EatInOrderRepository orderRepository) {
    this.orderTableRepository = orderTableRepository;
    this.orderRepository = orderRepository;
  }

  @Transactional
  public EatInOrderTable create(final String name) {
    if (Objects.isNull(name) || name.isEmpty()) {
      throw new IllegalArgumentException();
    }
    final EatInOrderTable orderTable = new EatInOrderTable();
    orderTable.setId(UUID.randomUUID());
    orderTable.setName(name);
    orderTable.setNumberOfGuests(0);
    orderTable.setOccupied(false);
    return orderTableRepository.save(orderTable);
  }

  @Transactional
  public EatInOrderTable sit(final UUID orderTableId) {
    final EatInOrderTable orderTable =
        orderTableRepository.findById(orderTableId).orElseThrow(NoSuchElementException::new);
    orderTable.setOccupied(true);
    return orderTable;
  }

  @Transactional
  public EatInOrderTable clear(final UUID orderTableId) {
    final EatInOrderTable orderTable =
        orderTableRepository.findById(orderTableId).orElseThrow(NoSuchElementException::new);
    if (orderRepository.existsByOrderTableAndStatusNot(orderTable, EatInOrderStatus.COMPLETED)) {
      throw new IllegalStateException();
    }
    orderTable.setNumberOfGuests(0);
    orderTable.setOccupied(false);
    return orderTable;
  }

  @Transactional
  public EatInOrderTable changeNumberOfGuests(
      final UUID orderTableId, final EatInOrderTable request) {
    final int numberOfGuests = request.getNumberOfGuests();
    if (numberOfGuests < 0) {
      throw new IllegalArgumentException();
    }
    final EatInOrderTable orderTable =
        orderTableRepository.findById(orderTableId).orElseThrow(NoSuchElementException::new);
    if (!orderTable.isOccupied()) {
      throw new IllegalStateException();
    }
    orderTable.setNumberOfGuests(numberOfGuests);
    return orderTable;
  }

  @Transactional(readOnly = true)
  public List<EatInOrderTable> findAll() {
    return orderTableRepository.findAll();
  }
}
