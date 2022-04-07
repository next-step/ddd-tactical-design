package kitchenpos;

import kitchenpos.eatinorders.tobe.eatinorder.domain.EatInOrder;
import kitchenpos.eatinorders.tobe.eatinorder.domain.OrderLineItem;
import kitchenpos.eatinorders.tobe.eatinorder.domain.Quantity;
import kitchenpos.eatinorders.tobe.eatinorder.ui.dto.CreateRequest;
import kitchenpos.eatinorders.tobe.eatinorder.ui.dto.OrderLineItemCreateRequest;
import kitchenpos.eatinorders.tobe.ordertable.domain.OrderTable;
import kitchenpos.menus.tobe.menu.domain.MenuId;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class EatInOrderFixture {

    public static OrderLineItem 주문상품() {
        return 주문상품(UUID.randomUUID());
    }

    public static OrderLineItem 주문상품(final UUID menuId) {
        return new OrderLineItem(new MenuId(menuId), new Quantity(1L));
    }

    public static EatInOrder 매장주문(final OrderTable orderTable) {
        return new EatInOrder(Arrays.asList(주문상품()), orderTable.getId());
    }

    public static EatInOrder 수락된_매장주문(final OrderTable orderTable) {
        final EatInOrder eatInOrder = 매장주문(orderTable);
        eatInOrder.accept();
        return eatInOrder;
    }

    public static EatInOrder 서빙된_매장주문(final OrderTable orderTable) {
        final EatInOrder eatInOrder = 수락된_매장주문(orderTable);
        eatInOrder.serve();
        return eatInOrder;
    }

    public static EatInOrder 완료된_매장주문(final OrderTable orderTable) {
        final EatInOrder eatInOrder = 서빙된_매장주문(orderTable);
        eatInOrder.complete();
        return eatInOrder;
    }

    public static EatInOrder 매장주문(final List<OrderLineItem> orderLineItems, final OrderTable orderTable) {
        return new EatInOrder(orderLineItems, orderTable.getId());
    }

    public static EatInOrder 수락된_매장주문(final List<OrderLineItem> orderLineItems, final OrderTable orderTable) {
        final EatInOrder eatInOrder = 매장주문(orderLineItems, orderTable);
        eatInOrder.accept();
        return eatInOrder;
    }

    public static EatInOrder 서빙된_매장주문(final List<OrderLineItem> orderLineItems, final OrderTable orderTable) {
        final EatInOrder eatInOrder = 수락된_매장주문(orderLineItems, orderTable);
        eatInOrder.serve();
        return eatInOrder;
    }

    public static EatInOrder 완료된_매장주문(final List<OrderLineItem> orderLineItems, final OrderTable orderTable) {
        final EatInOrder eatInOrder = 서빙된_매장주문(orderLineItems, orderTable);
        eatInOrder.complete();
        return eatInOrder;
    }

    public static CreateRequest createOrderRequest(final UUID orderTableId, final OrderLineItemCreateRequest... orderLineItems) {
        return createOrderRequest(orderTableId, Arrays.asList(orderLineItems));
    }

    public static CreateRequest createOrderRequest(final UUID orderTableId, final List<OrderLineItemCreateRequest> orderLineItems) {
        return new CreateRequest(orderTableId, orderLineItems);
    }

    public static OrderLineItemCreateRequest createOrderLineItemRequest(final UUID menuId, final long price, final long quantity) {
        return new OrderLineItemCreateRequest(menuId, quantity, BigDecimal.valueOf(price));
    }
}
