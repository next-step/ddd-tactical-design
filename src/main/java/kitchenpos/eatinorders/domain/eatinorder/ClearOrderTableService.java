package kitchenpos.eatinorders.domain.eatinorder;

import kitchenpos.common.annotation.DomainService;
import kitchenpos.common.domain.orders.OrderStatus;

@DomainService
public class ClearOrderTableService {

  private final OrderTableRepository orderTableRepository;


  public ClearOrderTableService(OrderTableRepository orderTableRepository) {
    this.orderTableRepository = orderTableRepository;
  }

  public void clear(final Order order) {
    if(order.isNotEatIn()){
      return;
    }

    if (orderTableRepository.existsByOrderTableAndStatausNot(order.getOrderTableId(), OrderStatus.COMPLETED)) {
      throw new IllegalStateException("빈 테이블은 방문한 고객 인원을 변경할 수 없다.");
    }

    order.clearOrderTable();
  }
}
