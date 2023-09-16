package kitchenpos.eatinorders.fixture;

import kitchenpos.common.domain.Price;
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
        return new EatInOrderLineItem(
                new MenuId(menu.getIdValue()),
                1L,
                menu.getPrice()
        );
    }

    public static EatInOrderLineItem orderLineItem(MenuId menuId, long price) {
        return new EatInOrderLineItem(
                menuId,
                1L,
                new Price(price)
        );
    }

    public static EatInOrderLineItem orderLineItem(UUID menuId, long price, long quantity) {
        return new EatInOrderLineItem(
                new MenuId(menuId),
                quantity,
                new Price(price)
        );
    }

    private static EatInOrderLineItems orderLineItems(EatInOrderLineItem... orderLineItems) {
        return new EatInOrderLineItems(Arrays.asList(orderLineItems));
    }

}
