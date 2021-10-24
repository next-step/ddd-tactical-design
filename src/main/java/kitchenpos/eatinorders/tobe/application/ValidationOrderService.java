package kitchenpos.eatinorders.tobe.application;

import kitchenpos.eatinorders.domain.OrderType;
import kitchenpos.eatinorders.tobe.domain.Menu;
import kitchenpos.eatinorders.tobe.ui.OrderLineItemForm;

import java.util.List;
import java.util.Objects;

public class ValidationOrderService {

    public static void validateOrderType(OrderType orderType) {
        if (Objects.isNull(orderType)) {
            throw new IllegalArgumentException("주문 유형은 필수 입니다.");
        }
    }

    public static void validateOrderLineItem(List<OrderLineItemForm> orderLineItems) {
        if (Objects.isNull(orderLineItems) || orderLineItems.isEmpty()) {
            throw new IllegalArgumentException("주문 메뉴는 필수 입니다.");
        }
    }

    public static void menuSizeEqualsOrderLineItemSize(List<Menu> menus, List<OrderLineItemForm> orderLineItemRequests) {
        if (menus.size() != orderLineItemRequests.size()) {
            throw new IllegalArgumentException("주문 메뉴와 메뉴 길이가 다릅니다.");
        }
    }
}
