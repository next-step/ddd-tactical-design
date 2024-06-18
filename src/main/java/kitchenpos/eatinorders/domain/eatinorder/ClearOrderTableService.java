package kitchenpos.eatinorders.domain.eatinorder;

import kitchenpos.common.annotation.DomainService;
import kitchenpos.common.domain.orders.OrderStatus;

@DomainService
public class ClearOrderTableService {

  private final OrderRepository orderRepository;
  private final OrderTableRepository orderTableRepository;


  public ClearOrderTableService(final OrderRepository orderRepository, OrderTableRepository orderTableRepository) {
    this.orderRepository = orderRepository;
    this.orderTableRepository = orderTableRepository;
  }

  public void clear(final Order order) {
    if (order.isNotEatIn()) {
      return;
    }
    if (orderTableRepository.findById(order.getOrderTableId()).isEmpty()) {
      throw new IllegalArgumentException("주문 테이블이 존재 하지 않습니다.");
    }

    if (orderRepository.existsByOrderTableAndStatusNot(order.getOrderTable(), OrderStatus.COMPLETED)) {
      throw new IllegalStateException("주문이 완료되지 않은 테이블이 있을때, 주문 테이블을 사용안함으로 변경할 수 없습니다.");
    }

    order.clearOrderTable();
  }
}
