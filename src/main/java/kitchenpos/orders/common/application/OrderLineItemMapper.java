package kitchenpos.orders.common.application;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.menus.domain.tobe.Menu;
import kitchenpos.orders.common.application.dto.OrderLineItemRequest;
import kitchenpos.orders.common.application.dto.OrderLineItemsRequest;
import kitchenpos.orders.common.domain.OrderType;
import kitchenpos.orders.common.domain.tobe.MenuQuantity;
import kitchenpos.orders.common.domain.tobe.OrderLineItem;
import kitchenpos.orders.common.domain.tobe.OrderLineItems;
import kitchenpos.orders.common.domain.tobe.OrderLineItemsValidator;
import org.springframework.stereotype.Component;

@Component
public class OrderLineItemMapper {

    private final MenuRepository menuRepository;
    private final OrderLineItemsValidator orderLineItemsValidator;

    public OrderLineItemMapper(MenuRepository menuRepository,
        OrderLineItemsValidator orderLineItemsValidator) {
        this.menuRepository = menuRepository;
        this.orderLineItemsValidator = orderLineItemsValidator;
    }

    public OrderLineItems map(OrderType orderType, OrderLineItemsRequest request) {
        List<UUID> menuIds = request.orderLineItems()
            .stream().map(OrderLineItemRequest::menuId)
            .toList();

        List<Menu> menus = menuRepository.findAllByIdIn(menuIds);
        List<OrderLineItem> orderLineItems = createOrderLineItems(orderType, request, menus);
        orderLineItemsValidator.validate(orderLineItems, menus);
        return new OrderLineItems(orderLineItems);
    }

    private List<OrderLineItem> createOrderLineItems(OrderType orderType,
        OrderLineItemsRequest request,
        List<Menu> menus) {
        return request.orderLineItems()
            .stream()
            .map(orderLineItem -> createOrderLineItem(menus,
                orderLineItem.menuId(),
                orderType,
                orderLineItem.quantity())
            )
            .toList();
    }

    private OrderLineItem createOrderLineItem(List<Menu> menus, UUID menuId, OrderType orderType,
        long quantity) {
        Menu menu = findMenu(menus, menuId);
        MenuQuantity menuQuantity = new MenuQuantity(orderType, quantity);
        return new OrderLineItem(menu, menuQuantity);
    }

    private Menu findMenu(List<Menu> menus, UUID menuId) {
        return menus.stream().filter(menu -> menu.getId().equals(menuId))
            .findFirst().orElseThrow((NoSuchElementException::new));
    }
}
