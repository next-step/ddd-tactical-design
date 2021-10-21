package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.eatinorders.domain.OrderType;
import kitchenpos.eatinorders.tobe.ui.OrderForm;
import kitchenpos.eatinorders.tobe.ui.OrderLineItemForm;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrderLineFactory {

    public TobeOrderLineItems findOrderLineItems(OrderForm request, List<Menu> menus) {
        List<OrderLineItemForm> orderLineItemRequests = request.getOrderLineItems();

        orderLineItemRequests.forEach(item -> {
            validationDeliveryItemQuantity(item, request.getType());
            validationMenuState(item, findMenu(item.getMenuId(), menus));
        });
        return new TobeOrderLineItems(createOrderLineItems(orderLineItemRequests, menus));
    }

    private List<TobeOrderLineItem> createOrderLineItems(List<OrderLineItemForm> orderLineItemRequests, List<Menu> menus) {
        return orderLineItemRequests.stream()
                .map(item ->
                    new TobeOrderLineItem(
                        item.getSeq(),
                        findMenu(item.getMenuId(), menus),
                        item.getQuantity(),
                        item.getPrice()
                    )
                )
                .collect(Collectors.toList());
    }

    private Menu findMenu(UUID menuId, List<Menu> menus) {
        return menus.stream()
                .filter(orderMenu -> orderMenu.getId().equals(menuId))
                .findAny()
                .orElseThrow(NoSuchElementException::new);
    }

    private void validationDeliveryItemQuantity(OrderLineItemForm item, OrderType type) {
        if (type != OrderType.EAT_IN && item.getQuantity() < 0) {
            throw new IllegalArgumentException("주문 수량이 없습니다.");
        }
    }

    private void validationMenuState(OrderLineItemForm item, Menu menu) {
        if (!menu.isDisplayed()) {
            throw new IllegalStateException("잘못된 메뉴가 있습니다.");
        }

        if (menu.getMenuPrice().compareTo(item.getPrice()) != 0) {
            throw new IllegalArgumentException("주문 상품 금액과 메뉴 금액이 서로 다릅니다.");
        }
    }
}
