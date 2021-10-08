package kitchenpos.fixture;

import kitchenpos.eatinorders.tobe.eatinorder.domain.EatInOrder;
import kitchenpos.eatinorders.tobe.eatinorder.domain.OrderLineItem;
import kitchenpos.eatinorders.tobe.eatinorder.domain.OrderStatus;
import kitchenpos.eatinorders.tobe.eatinorder.domain.Quantity;
import kitchenpos.eatinorders.tobe.ordertable.domain.OrderTable;
import kitchenpos.menus.tobe.menu.domain.Menu;
import kitchenpos.menus.tobe.menu.domain.MenuId;

import java.util.Arrays;
import java.util.UUID;

public class EatInOrderFixture {

    public static OrderLineItem 주문상품() {
        return 주문상품(UUID.randomUUID());
    }

    public static OrderLineItem 주문상품(final UUID menuId) {
        return new OrderLineItem(new MenuId(menuId), new Quantity(1L));
    }

    public static OrderLineItem 주문상품(final Menu menu) {
        return new OrderLineItem(new MenuId(menu.getId()), new Quantity(1L));
    }

    public static EatInOrder 매장주문(final OrderStatus status, final OrderTable orderTable) {
        return new EatInOrder(Arrays.asList(주문상품()), status, orderTable);
    }

}
