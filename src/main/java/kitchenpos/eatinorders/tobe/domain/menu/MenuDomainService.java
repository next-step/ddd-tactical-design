package kitchenpos.eatinorders.tobe.domain.menu;

import kitchenpos.deliveryorders.infra.KitchenridersClient;
import kitchenpos.eatinorders.tobe.domain.Order;
import kitchenpos.eatinorders.tobe.domain.OrderLineItem;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static kitchenpos.eatinorders.tobe.domain.OrderStatus.WAITING;
import static kitchenpos.eatinorders.tobe.domain.OrderType.DELIVERY;

@Component
public class MenuDomainService {
    private final MenuTranslator menuTranslator;
    private final KitchenridersClient kitchenridersClient;

    public MenuDomainService(final MenuTranslator menuTranslator, final KitchenridersClient kitchenridersClient) {
        this.menuTranslator = menuTranslator;
        this.kitchenridersClient = kitchenridersClient;
    }

    public void deliver(final Order order) {
        if (order.getStatus() != WAITING) {
            throw new IllegalStateException("접수 대기 중인 주문은 접수 할 수 없습니다.");
        }
        if (order.getType() == DELIVERY) {
            final BigDecimal sum = order.getOrderLineItems().stream()
                    .map(item -> getMenu(item).getPrice()
                            .multiply(BigDecimal.valueOf(item.getQuantity()))
                    ).reduce(BigDecimal.ZERO, BigDecimal::add);
            kitchenridersClient.requestDelivery(order.getId(), sum, order.getDeliveryAddress());
        }
    }

    public void validateOrder(final Order order) {
        final List<OrderLineItem> orderLineItems = order.getOrderLineItems();
        final List<Menu> menus = menuTranslator.getMenus(orderLineItems.stream()
                .map(OrderLineItem::getMenuId)
                .collect(Collectors.toList()));
        menus.forEach(this::validateMenu);
        if (menus.size() != orderLineItems.size()) {
            throw new IllegalArgumentException("부적절한 메뉴는 주문하할 수 없습니다.");
        }
    }

    private void validateMenu(final Menu menu) {
        if (!menu.isDisplayed()) {
            throw new IllegalStateException("숨겨진 메뉴입니다.");
        }
    }

    private Menu getMenu(final OrderLineItem orderLineItem) {
        final Menu menu = menuTranslator.getMenu(orderLineItem.getMenuId());
        validateMenu(menu);
        if (menu.getPrice().compareTo(orderLineItem.getPrice()) != 0) {
            throw new IllegalArgumentException("메뉴의 가격과 주문 항목의 가격은 같아야 합니다.");
        }
        return menu;
    }
}
