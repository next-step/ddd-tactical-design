package kitchenpos.eatinorders.order.tobe.domain;

import kitchenpos.common.annotation.DomainService;
import kitchenpos.common.domain.vo.Price;
import kitchenpos.eatinorders.order.tobe.domain.vo.MenuSpecification;
import kitchenpos.eatinorders.order.tobe.domain.vo.OrderLineItemSpecification;

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

    public EatInOrder create(final UUID orderTableId, final List<OrderLineItemSpecification> orderLineItemSpecifications) {
        if (orderTableManager.isEmptyTable(orderTableId)) {
            throw new IllegalStateException("사용중인 주문테이블만 매장 주문이 가능합니다.");
        }
        final List<EatInOrderLineItem> eatInOrderItems = createEatInOrderItems(orderLineItemSpecifications);
        return EatInOrder.create(orderTableId, EatInOrderLineItems.of(eatInOrderItems));
    }

    private List<EatInOrderLineItem> createEatInOrderItems(final List<OrderLineItemSpecification> orderLineItemSpecifications) {
        return orderLineItemSpecifications.stream()
                .map(this::createEatInOrderItem)
                .collect(Collectors.toUnmodifiableList());
    }

    private EatInOrderLineItem createEatInOrderItem(final OrderLineItemSpecification orderLineItemSpecification) {
        final MenuSpecification menuSpecification = menuContextClient.findOrderMenuItemById(orderLineItemSpecification.getMenuId());
        if (isNotDisplayed(menuSpecification)) {
            throw new IllegalStateException("전시 중인 메뉴만 주문이 가능합니다.");
        }
        final Price price = Price.valueOf(orderLineItemSpecification.getPrice());
        if (isNotSame(price, menuSpecification.price())) {
            throw new IllegalArgumentException("주문상품의 가격은 메뉴 가격과 동일해야 합니다.");
        }
        return EatInOrderLineItem.create(orderLineItemSpecification.getMenuId(), price, orderLineItemSpecification.getQuantity());
    }

    private boolean isNotDisplayed(final MenuSpecification menuSpecification) {
        return !menuSpecification.isDisplayed();
    }

    private boolean isNotSame(final Price price, final Price anotherPrice) {
        return !price.equals(anotherPrice);
    }
}
