package kitchenpos.fixture;

import java.util.Arrays;
import java.util.List;
import kitchenpos.menus.domain.tobe.Menu;
import kitchenpos.orders.common.domain.OrderType;
import kitchenpos.orders.common.domain.tobe.MenuQuantity;
import kitchenpos.orders.common.domain.tobe.OrderLineItem;
import kitchenpos.orders.common.domain.tobe.OrderLineItems;
import kitchenpos.orders.store.domain.tobe.OrderTable;
import kitchenpos.orders.store.domain.tobe.StoreOrder;

public class StoreOrderFixture {

    public static StoreOrder createStoreOrder() {
        OrderTable orderTable = OrderTableFixture.createNumber1();
        orderTable.sit();

        Menu menu = MenuFixture.createFriedOnePlusOne(MenuGroupFixture.createChicken(),
            ProductFixture.createFired());
        return new StoreOrder(createOrderLineItems(menu), orderTable);
    }

    public static StoreOrder createStoreOrder(OrderTable orderTable, Menu... menu) {
        return new StoreOrder(createOrderLineItems(menu), orderTable);
    }

    public static OrderLineItems createOrderLineItems(Menu... menu) {
        List<OrderLineItem> orderLineItems = Arrays.stream(menu)
            .map(StoreOrderFixture::createOrderLineItem).toList();
        return new OrderLineItems(orderLineItems);
    }

    public static OrderLineItem createOrderLineItem(Menu menu) {
        return new OrderLineItem(menu, new MenuQuantity(OrderType.EAT_IN, 1));
    }
}
