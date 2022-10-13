package kitchenpos.eatinorders.tobe.domain;

import java.util.NoSuchElementException;
import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuRepository;
import org.springframework.stereotype.Component;

@Component
public class DefaultOrderValidator implements OrderValidator {

  private final MenuRepository menuRepository;
  private final OrderRepository orderRepository;
  private final OrderTableRepository orderTableRepository;

  public DefaultOrderValidator(
      MenuRepository menuRepository,
      OrderRepository orderRepository,
      OrderTableRepository orderTableRepository
  ) {
    this.orderRepository = orderRepository;
    this.menuRepository = menuRepository;
    this.orderTableRepository = orderTableRepository;
  }

  @Override
  public void validate(Order order) {
    order.getOrderLineItems()
        .forEach(this::orderLineItemValidate);
  }

  private void orderLineItemValidate(OrderLineItem orderLineItem) {
    Menu menu = menuRepository.findById(orderLineItem.getMenuId())
        .orElseThrow(NoSuchElementException::new);

    if (!menu.isDisplayed()) {
      throw new IllegalStateException();
    }
  }

}