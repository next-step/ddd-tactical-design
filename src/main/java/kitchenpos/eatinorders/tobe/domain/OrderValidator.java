package kitchenpos.eatinorders.tobe.domain;

import java.util.NoSuchElementException;
import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuRepository;
import org.springframework.stereotype.Component;

@Component
public class OrderValidator {

  private final MenuRepository menuRepository;

  public OrderValidator(MenuRepository menuRepository) {
    this.menuRepository = menuRepository;
  }

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
