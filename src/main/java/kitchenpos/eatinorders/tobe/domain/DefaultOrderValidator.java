package kitchenpos.eatinorders.tobe.domain;

import java.util.NoSuchElementException;
import kitchenpos.eatinorders.domain.OrderRepository;
import kitchenpos.eatinorders.domain.OrderTableRepository;
import kitchenpos.menus.domain.MenuRepository;
import org.springframework.stereotype.Component;

@Component
public class DefaultOrderValidator implements OrderValidator {

  private final OrderRepository orderRepository;
  private final MenuRepository menuRepository;
  private final OrderTableRepository orderTableRepository;

  public DefaultOrderValidator(
      OrderRepository orderRepository,
      MenuRepository menuRepository,
      OrderTableRepository orderTableRepository
  ) {
    this.orderRepository = orderRepository;
    this.menuRepository = menuRepository;
    this.orderTableRepository = orderTableRepository;
  }

  @Override
  public void validate(Order order) {
    order.getOrderLineItems()
        .forEach(orderLineItem -> menuRepository.findById(orderLineItem.getMenuId())
            .orElseThrow(NoSuchElementException::new)
        );
  }

}