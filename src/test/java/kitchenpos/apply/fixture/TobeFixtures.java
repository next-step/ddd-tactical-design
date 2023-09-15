package kitchenpos.apply.fixture;


import kitchenpos.apply.menus.tobe.domain.Menu;
import kitchenpos.apply.order.eatinorders.tobe.domain.*;
import kitchenpos.apply.order.eatinorders.tobe.ui.dto.EatInOrderRequest;
import kitchenpos.apply.order.eatinorders.tobe.ui.dto.OrderLineItemRequest;
import kitchenpos.apply.order.eatinorders.tobe.ui.dto.OrderTableRequest;
import kitchenpos.apply.products.tobe.domain.Product;
import kitchenpos.apply.products.tobe.domain.ProductName;
import kitchenpos.apply.products.tobe.ui.ProductRequest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static kitchenpos.apply.fixture.MenuFixture.menu;

public class TobeFixtures {

    public static Product product() {
        return product("후라이드", 16_000L);
    }

    public static Product product(final String name, final long price) {
        ProductName productName = new ProductName(name);
        return Product.of(productName, BigDecimal.valueOf(price));
    }

    public static ProductRequest productRequest() {
        return productRequest("후라이드", 16_000L);
    }

    public static ProductRequest productRequest(final String name, final long price) {
        return new ProductRequest(name, BigDecimal.valueOf(price));
    }

    public static ProductRequest productRequest(final String name, final BigDecimal price) {
        return new ProductRequest(name, price);
    }

    public static EatInOrder eatInOrder(final EatInOrderStatus status) {
        OrderLineItems orderLineItems = new OrderLineItems(List.of(orderLineItem()));
        final EatInOrder eatInOrder = EatInOrder.of(orderLineItems, orderTable());
        switch (status) {
            case ACCEPTED:
                eatInOrder.accepted();
                break;
            case SERVED:
                eatInOrder.serve();
                break;
            case COMPLETED:
                eatInOrder.complete();
                break;
        }
        return eatInOrder;
    }

    public static EatInOrder eatInOrder(final EatInOrderStatus status, final OrderTable orderTable) {
        OrderLineItems orderLineItems = new OrderLineItems(List.of(orderLineItem()));
        final EatInOrder eatInOrder = EatInOrder.of(orderLineItems, orderTable);
        switch (status) {
            case ACCEPTED:
                eatInOrder.accepted();
                break;
            case SERVED:
                eatInOrder.serve();
                break;
            case COMPLETED:
                eatInOrder.complete();
                break;
        }
        return eatInOrder;
    }

    public static EatInOrderRequest eatInOrderRequest(OrderLineItemRequest... orderLineItemRequests) {
        return new EatInOrderRequest(
                EatInOrderStatus.WAITING,
                LocalDateTime.now(),
                List.of(orderLineItemRequests),
                UUID.fromString(orderTable().getId()));
    }

    public static EatInOrderRequest eatInOrderRequest(List<OrderLineItemRequest> orderLineItemRequests) {
        return new EatInOrderRequest(
                EatInOrderStatus.WAITING,
                LocalDateTime.now(),
                orderLineItemRequests,
                UUID.fromString(orderTable().getId()));
    }

    public static EatInOrderRequest eatInOrderRequest(final UUID orderTableId, OrderLineItemRequest... orderLineItemRequests) {
        return new EatInOrderRequest(
                EatInOrderStatus.WAITING,
                LocalDateTime.now(),
                List.of(orderLineItemRequests),
                orderTableId);
    }

    public static OrderLineItem orderLineItem() {
        Menu menu = menu();
        return OrderLineItem.of(UUID.fromString(menu.getId()), 1L, BigDecimal.valueOf(19_000));
    }

    public static OrderLineItemRequest orderLineItemRequest(UUID menuId, long quantity, long price) {
        return new OrderLineItemRequest(0L, menuId, quantity, new BigDecimal(price));
    }

    public static OrderTable orderTable() {
        return OrderTable.of("기본 테이블");
    }

    public static OrderTable orderTable(final boolean occupied, final int numberOfGuests) {
        final OrderTable orderTable = OrderTable.of("1번");
        if (occupied) {
            orderTable.sit();
        }
        orderTable.changeNumberOfGuest(numberOfGuests);
        return orderTable;
    }

    public static OrderTableRequest orderTableRequest(final String name) {
        return orderTableRequest(name, false, 0);
    }

    public static OrderTableRequest orderTableRequest(final int numberOfGuests) {
        return orderTableRequest("기본 테이블", false, numberOfGuests);
    }

    public static OrderTableRequest orderTableRequest(final boolean occupied, final int numberOfGuests) {
        return orderTableRequest("1번", occupied, numberOfGuests);
    }

    public static OrderTableRequest orderTableRequest(final String name, final boolean occupied, final int numberOfGuests) {
        return new OrderTableRequest(name, numberOfGuests, occupied);
    }

}
