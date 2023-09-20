package kitchenpos.order.domain;

import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.order.eatinorders.domain.vo.OrderLineItems;
import kitchenpos.support.ValueObject;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Component
public class OrderLineItemsService extends ValueObject {

    private final MenuRepository menuRepository;


    public OrderLineItemsService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    private static void validQuantity(List<OrderLineItem> orderLineItems, OrderType orderType) {
        if (orderType != OrderType.EAT_IN) {
            orderLineItems.stream()
                    .map(OrderLineItem::getQuantity)
                    .forEach(quantity -> {
                        if (quantity < 0) {
                            throw new IllegalArgumentException();
                        }
                    });
        }
    }

    public OrderLineItems getOrderLineItems(List<OrderLineItem> orderLineItems, OrderType orderType) {
        validMenuSize(orderLineItems);
        validQuantity(orderLineItems, orderType);
        return new OrderLineItems(orderLineItems.stream()
                .map(eatInOrderLineItem -> new OrderLineItem(getMenu(eatInOrderLineItem), eatInOrderLineItem.getQuantity(), eatInOrderLineItem.getPrice()))
                .collect(Collectors.toList()));
    }

    private void validMenuSize(List<OrderLineItem> orderLineItems) {
        final List<Menu> menus = menuRepository.findAllByIdIn(
                orderLineItems.stream()
                        .map(OrderLineItem::getMenuId)
                        .collect(Collectors.toList()));
        if (menus.size() != orderLineItems.size()) {
            throw new IllegalArgumentException();
        }
    }


    private Menu getMenu(OrderLineItem orderLineItem) {
        final Menu menu = menuRepository.findById(orderLineItem.getMenuId())
                .orElseThrow(NoSuchElementException::new);
        if (!menu.isDisplayed()) {
            throw new IllegalStateException();
        }
        if (menu.getPrice().compareTo(orderLineItem.getPrice()) != 0) {
            throw new IllegalArgumentException();
        }
        return menu;
    }
}
