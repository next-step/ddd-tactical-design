package kitchenpos.eatinorders.tobe.domain.application;

import java.util.UUID;
import kitchenpos.eatinorders.tobe.domain.ordertable.OrderTable;
import kitchenpos.eatinorders.tobe.domain.ordertable.OrderTableRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderTableService {
  private final OrderTableRepository orderTableRepository;

  public OrderTableService(OrderTableRepository orderTableRepository) {
    this.orderTableRepository = orderTableRepository;
  }

  public void completeOrder(UUID orderTableId) {
    OrderTable orderTable = orderTableRepository.findById(orderTableId)
        .orElseThrow();
    orderTable.clear(false);
  }
}
