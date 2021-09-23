package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.deliveryorders.infra.KitchenridersClient;
import kitchenpos.eatinorders.tobe.dto.MenuResponse;
import kitchenpos.eatinorders.tobe.dto.OrderLineItemResponse;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static kitchenpos.eatinorders.tobe.domain.OrderStatus.WAITING;
import static kitchenpos.eatinorders.tobe.domain.OrderType.DELIVERY;
import static kitchenpos.eatinorders.tobe.domain.OrderType.EAT_IN;

@Component
public class OrderDomainService {
    private final MenuTranslator menuTranslator;
    private final KitchenridersClient kitchenridersClient;

    public OrderDomainService(final MenuTranslator menuTranslator, final KitchenridersClient kitchenridersClient) {
        this.menuTranslator = menuTranslator;
        this.kitchenridersClient = kitchenridersClient;
    }

    public List<OrderLineItemResponse> getOrderLineItems(final Order order) {
        return order.getOrderLineItems().stream()
                .map(item -> new OrderLineItemResponse(
                        item.getSeq(),
                        item.getQuantity(),
                        item.getPrice(),
                        item.getMenuId(),
                        getMenu(item))
                ).collect(Collectors.toList());
    }

    void deliver(final Order order) {
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
        final List<MenuResponse> menus = menuTranslator.getMenus(orderLineItems.stream()
                .map(OrderLineItem::getMenuId)
                .collect(Collectors.toList()));
        menus.forEach(this::validateMenu);
        if (menus.size() != orderLineItems.size()) {
            throw new IllegalArgumentException("부적절한 메뉴는 주문하할 수 없습니다.");
        }
        if (order.getType() != EAT_IN) {
            orderLineItems.stream()
                    .map(OrderLineItem::getQuantity)
                    .forEach(this::validateQuantity);
        }
    }

    private void validateMenu(final MenuResponse menu) {
        if (!menu.isDisplayed()) {
            throw new IllegalStateException("숨겨진 메뉴입니다.");
        }
    }

    private void validateQuantity(final long quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("매장 식사 주문은 수량이 1개 이상이어야 합니다.");
        }
    }

    private MenuResponse getMenu(final OrderLineItem orderLineItem) {
        final MenuResponse menu = menuTranslator.getMenu(orderLineItem.getMenuId());
        validateMenu(menu);
        if (menu.getPrice().compareTo(orderLineItem.getPrice()) != 0) {
            throw new IllegalArgumentException("메뉴의 가격과 주문 항목의 가격은 같아야 합니다.");
        }
        return menu;
    }
}
