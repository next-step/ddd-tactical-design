package kitchenpos.eatinorders.tobe.domain.order.eventlistener;

import java.util.UUID;
import kitchenpos.eatinorders.tobe.domain.order.event.OrderCompletionEvent;
import kitchenpos.eatinorders.tobe.domain.ordertable.OrderTable;
import kitchenpos.eatinorders.tobe.domain.ordertable.OrderTableRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventListener {

  private final OrderTableRepository orderTableRepository;

  public OrderEventListener(OrderTableRepository orderTableRepository) {
    this.orderTableRepository = orderTableRepository;
  }

  @EventListener
  public void completeOrder(OrderCompletionEvent event) {
    UUID orderTableId = event.getOrderTableId();
    OrderTable orderTable = orderTableRepository.findById(orderTableId)
        .orElseThrow();
    orderTable.clear(false);
  }
}
