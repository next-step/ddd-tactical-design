package kitchenpos.eatinorders.tobe.domain.application.eventlistener;

import java.util.UUID;
import kitchenpos.eatinorders.tobe.domain.application.OrderTableService;
import kitchenpos.eatinorders.tobe.domain.order.event.OrderCompletionEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventListener {
  private final OrderTableService orderTableService;

  public OrderEventListener(OrderTableService orderTableService) {
    this.orderTableService = orderTableService;
  }

  @EventListener
  public void completeOrder(OrderCompletionEvent event) {
    UUID orderTableId = event.getOrderTableId();
    orderTableService.completeOrder(orderTableId);
  }
}
