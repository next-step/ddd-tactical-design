package kitchenpos.eatinorders.order.tobe.domain;

import kitchenpos.common.annotation.DomainService;
import kitchenpos.common.domain.vo.Price;
import kitchenpos.eatinorders.order.tobe.domain.vo.EatInOrderMenu;
import kitchenpos.eatinorders.order.tobe.domain.vo.OrderLineItem;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@DomainService
public class EatInOrderFactory {

    private final MenuContextClient menuContextClient;
    private final OrderTableManager orderTableManager;

    public EatInOrderFactory(MenuContextClient menuContextClient, OrderTableManager orderTableManager) {
        this.menuContextClient = menuContextClient;
        this.orderTableManager = orderTableManager;
    }

    public EatInOrder create(final UUID orderTableId, final List<OrderLineItem> orderLineItems) {
        if (orderTableManager.isEmptyTable(orderTableId)) {
            throw new IllegalStateException("사용중인 주문테이블만 매장 주문이 가능합니다.");
        }
        final List<EatInOrderLineItem> eatInOrderItems = createEatInOrderItems(orderLineItems);
        return EatInOrder.create(orderTableId, EatInOrderLineItems.of(eatInOrderItems));
    }

    private List<EatInOrderLineItem> createEatInOrderItems(final List<OrderLineItem> orderLineItems) {
        return orderLineItems.stream()
                .map(this::createEatInOrderItem)
                .collect(Collectors.toUnmodifiableList());
    }

    private EatInOrderLineItem createEatInOrderItem(final OrderLineItem orderLineItem) {
        final EatInOrderMenu eatInOrderMenu = menuContextClient.findOrderMenuItemById(orderLineItem.getMenuId());
        if (isNotDisplayed(eatInOrderMenu)) {
            throw new IllegalStateException("전시 중인 메뉴만 주문이 가능합니다.");
        }
        final Price price = Price.valueOf(orderLineItem.getPrice());
        if (isNotSame(price, eatInOrderMenu.price())) {
            throw new IllegalArgumentException("주문상품의 가격은 메뉴 가격과 동일해야 합니다.");
        }
        return EatInOrderLineItem.create(orderLineItem.getMenuId(), price, orderLineItem.getQuantity());
    }

    private boolean isNotDisplayed(final EatInOrderMenu eatInOrderMenu) {
        return !eatInOrderMenu.isDisplayed();
    }

    private boolean isNotSame(final Price price, final Price anotherPrice) {
        return !price.equals(anotherPrice);
    }
}
