package kitchenpos.support.application;

import kitchenpos.support.dto.OrderLineItemCreateRequest;
import kitchenpos.eatinorders.exception.KitchenPosIllegalArgumentException;
import kitchenpos.eatinorders.exception.KitchenPosIllegalStateException;
import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.support.domain.MenuClient;
import kitchenpos.support.domain.OrderLineItem;
import kitchenpos.support.domain.OrderLineItems;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static kitchenpos.eatinorders.exception.KitchenPosExceptionMessage.INVALID_ORDER_LINE_ITEMS_SIZE;
import static kitchenpos.eatinorders.exception.KitchenPosExceptionMessage.INVALID_ORDER_LINE_ITEM_SIZE;
import static kitchenpos.eatinorders.exception.KitchenPosExceptionMessage.MENU_IS_HIDE;
import static kitchenpos.eatinorders.exception.KitchenPosExceptionMessage.MENU_PRICE_IS_NOT_SAME;

public class OrderLineItemsFactory {
    private final MenuClient menuClient;

    public OrderLineItemsFactory(MenuClient menuClient) {
        this.menuClient = menuClient;
    }

    public OrderLineItems create(final List<OrderLineItemCreateRequest> orderLineItemsRequest) {
        checkNullOrEmptyOrderLineItems(orderLineItemsRequest);
        checkDuplicatedMenu(orderLineItemsRequest);
        return new OrderLineItems(mapping(orderLineItemsRequest));
    }

    private List<OrderLineItem> mapping(List<OrderLineItemCreateRequest> orderLineItems) {
        orderLineItems.forEach(this::checkMenu);
        return orderLineItems.stream()
                .map(OrderLineItem::new)
                .toList();
    }

    private void checkMenu(OrderLineItemCreateRequest orderLineItem) {
        Menu menu = menuClient.getMenu(orderLineItem.menuId());
        checkHiddenMenu(menu);
        checkDisMatchMenuPrice(orderLineItem.price(), menu);
    }

    private void checkDuplicatedMenu(List<OrderLineItemCreateRequest> orderLineItemsRequest) {
        int menusSize = menuClient.countMenusByIdIn(getMenuIds(orderLineItemsRequest));
        int orderLineItemSize = orderLineItemsRequest.size();
        if (menusSize != orderLineItemSize) {
            throw new KitchenPosIllegalArgumentException(INVALID_ORDER_LINE_ITEM_SIZE, menusSize, orderLineItemSize);
        }
    }

    private List<UUID> getMenuIds(final List<OrderLineItemCreateRequest> orderLineItemsRequest) {
        return orderLineItemsRequest.stream()
                .map(OrderLineItemCreateRequest::menuId)
                .toList();
    }

    private void checkNullOrEmptyOrderLineItems(List<OrderLineItemCreateRequest> orderLineItemsRequest) {
        if (Objects.isNull(orderLineItemsRequest) || orderLineItemsRequest.isEmpty()) {
            throw new KitchenPosIllegalArgumentException(INVALID_ORDER_LINE_ITEMS_SIZE);
        }
    }

    private void checkDisMatchMenuPrice(BigDecimal menuPriceRequest, Menu menu) {
        if (menu.getPrice().compareTo(menuPriceRequest) != 0) {
            throw new KitchenPosIllegalArgumentException(
                    MENU_PRICE_IS_NOT_SAME, menu.getId(), menu.getPrice(), menuPriceRequest);
        }
    }

    private void checkHiddenMenu(Menu menu) {
        if (!menu.isDisplayed()) {
            throw new KitchenPosIllegalStateException(MENU_IS_HIDE, menu.getId());
        }
    }
}
