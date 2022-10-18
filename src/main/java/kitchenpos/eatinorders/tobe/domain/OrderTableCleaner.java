package kitchenpos.eatinorders.tobe.domain;

import java.util.NoSuchElementException;
import java.util.UUID;

public class OrderTableCleaner {

  private final OrderRepository orderRepository;
  private final OrderTableRepository orderTableRepository;


  public OrderTableCleaner(OrderRepository orderRepository,
      OrderTableRepository orderTableRepository) {
    this.orderRepository = orderRepository;
    this.orderTableRepository = orderTableRepository;
  }

  public void clear(UUID orderTableId) {
    OrderTable orderTable = orderTableRepository.findById(orderTableId)
        .orElseThrow(NoSuchElementException::new);
    if (orderRepository.existsByOrderTableAndStatusNot(orderTable.getId(), OrderStatus.COMPLETED)) {
      throw new IllegalStateException();
    }
    orderTable.clear();
  }
}
