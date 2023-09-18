package kitchenpos.eatinorders.fixture;

import kitchenpos.common.domain.Price;
import kitchenpos.eatinorders.application.OrderLinePolicy;
import kitchenpos.eatinorders.domain.*;
import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.ordertables.domain.OrderTable;

import java.util.Arrays;
import java.util.UUID;

import static kitchenpos.menus.application.fixtures.MenuFixture.menu;

public class EatInOrderFixture {

    public static EatInOrder order(final EatInOrderStatus status, final OrderTable orderTable) {
        return new EatInOrder(
                status,
                orderLineItems(orderLineItem()),
                new OrderTableId(orderTable.getIdValue())
        );
    }

    public static EatInOrder order(final EatInOrderStatus status, final OrderTable orderTable, EatInOrderLineItem... orderLineItems) {
        return new EatInOrder(
                status,
                orderLineItems(orderLineItems),
                new OrderTableId(orderTable.getIdValue())
        );
    }

    public static EatInOrder order(final OrderTable orderTable, EatInOrderLineItem... orderLineItems) {
        return new EatInOrder(
                orderLineItems(orderLineItems),
                new OrderTableId(orderTable.getIdValue())
        );
    }

    public static EatInOrderLineItem orderLineItem() {
        Menu menu = menu();
        return orderLineItem(
                new MenuId(menu.getIdValue()),
                1L,
                menu.getPriceValue().longValue(),
                (menuId, price) -> {}
        );
    }

    public static EatInOrderLineItem orderLineItem(MenuId menuId, long price, OrderLinePolicy policy) {
        return orderLineItem(
                menuId,
                1L,
                price,
                policy
        );
    }

    public static EatInOrderLineItem orderLineItem(MenuId menuId, long quantity, long price, OrderLinePolicy policy) {
        return new EatInOrderLineItem(
                menuId,
                quantity,
                new Price(price),
                policy
        );
    }


    public static EatInOrderLineItem orderLineItem(UUID menuId, long price, long quantity) {
        return orderLineItem(
                new MenuId(menuId),
                quantity,
                price,
                (menuUUD, orderPrice) -> {}
        );
    }

    public static EatInOrderLineItems orderLineItems(EatInOrderLineItem... orderLineItems) {
        return new EatInOrderLineItems(Arrays.asList(orderLineItems));
    }

}
