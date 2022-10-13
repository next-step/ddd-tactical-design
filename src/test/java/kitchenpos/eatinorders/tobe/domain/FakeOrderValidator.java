package kitchenpos.eatinorders.tobe.domain;

import static kitchenpos.Fixtures.INVALID_ID;

import kitchenpos.eatinorders.domain.OrderRepository;
import kitchenpos.eatinorders.domain.OrderTableRepository;
import kitchenpos.menus.domain.MenuRepository;

public class FakeOrderValidator implements OrderValidator {

  private final MenuRepository menuRepository;
  private final OrderRepository orderRepository;
  private final OrderTableRepository orderTableRepository;

  public FakeOrderValidator(MenuRepository menuRepository, OrderRepository orderRepository,
      OrderTableRepository orderTableRepository) {
    this.menuRepository = menuRepository;
    this.orderRepository = orderRepository;
    this.orderTableRepository = orderTableRepository;
  }

  @Override
  public void validate(Order order) {
    order.getOrderLineItems()
        .forEach(this::orderLineItemValidate);
  }

  private void orderLineItemValidate(OrderLineItem orderLineItem) {
    if (orderLineItem.getMenuId().equals(INVALID_ID)) {
      throw new IllegalArgumentException();
    }
  }
}
