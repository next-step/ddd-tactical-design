package kitchenpos.order.domain;

import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Component
public class OrderLineItemsValidService {

    private final MenuRepository menuRepository;

    public OrderLineItemsValidService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public void valid(OrderLineItems orderLineItems) {
        validMenu(orderLineItems);
        validMenuSize(orderLineItems);

    }

    private void validMenuSize(OrderLineItems orderLineItems) {
        final List<Menu> menus = menuRepository.findAllByIdIn(
                orderLineItems.getOrderLineItems().stream()
                        .map(OrderLineItem::getMenuId)
                        .collect(Collectors.toList()));
        if (menus.size() != orderLineItems.getOrderLineItems().size()) {
            throw new IllegalArgumentException();
        }
    }

    private void validMenu(OrderLineItems orderLineItems) {
        orderLineItems.getOrderLineItems().forEach(this::validMenu);
    }

    private void validMenu(OrderLineItem orderLineItem) {
        final Menu menu = menuRepository.findById(orderLineItem.getMenuId())
                .orElseThrow(NoSuchElementException::new);
        if (!menu.isDisplayed()) {
            throw new IllegalStateException();
        }
        if (menu.getPrice().compareTo(orderLineItem.getPrice()) != 0) {
            throw new IllegalArgumentException();
        }
    }
}
