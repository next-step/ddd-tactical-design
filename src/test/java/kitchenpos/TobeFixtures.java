package kitchenpos;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import kitchenpos.eatinorders.tobe.domain.EatInOrder;
import kitchenpos.eatinorders.tobe.domain.OrderLineItem;
import kitchenpos.eatinorders.tobe.domain.OrderLineItems;
import kitchenpos.eatinorders.tobe.domain.OrderTable;
import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuGroup;
import kitchenpos.menus.tobe.domain.MenuProduct;
import kitchenpos.products.infra.FakePurgomalumClient;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.Product;
import org.springframework.test.util.ReflectionTestUtils;

public class TobeFixtures {

    private static final PurgomalumClient purgomalumClient = new FakePurgomalumClient();

    public static Product newProduct(String name, long price) {
        return Product.of(name, price, purgomalumClient);
    }

    public static MenuProduct newMenuProduct(String name, long price) {
        return MenuProduct.create(newProduct(name, price), 1L);
    }

    public static MenuGroup newMenuGroup(String name) {
        return MenuGroup.create(name, purgomalumClient);
    }

    public static Menu newMenu(String name, Long price, MenuGroup menuGroup, List<MenuProduct> menuProducts) {
        return Menu.create(name, purgomalumClient, price, menuGroup, true, menuProducts);
    }

    public static OrderTable newOrderTable(String name) {
        return newOrderTable(name, Collections.emptyList());
    }

    public static OrderTable newOrderTable(String name, List<EatInOrder> eatInOrders) {
        OrderTable orderTable = OrderTable.create(name);
        ReflectionTestUtils.setField(orderTable, "id", UUID.randomUUID());

        eatInOrders.forEach(order -> ReflectionTestUtils.setField(order, "orderTableId", orderTable.getId()));

        ReflectionTestUtils.setField(orderTable, "eatInOrders", eatInOrders);

        orderTable.sit();
        orderTable.changeNumberOfGuests(5);
        return orderTable;
    }

    public static OrderLineItem newOrderLineItem(String name, Long price, Long quantity) {
        return OrderLineItem.create(newMenu(name, price, newMenuGroup("menuGroup"), Collections.singletonList(newMenuProduct("name", price))), quantity);
    }

    public static OrderLineItems newOrderLineItems(OrderLineItem... items) {
        return new OrderLineItems(Arrays.asList(items));
    }

    public static EatInOrder newOrder() {
        OrderLineItems orderLineItems = newOrderLineItems(newOrderLineItem("item", 1000L, 1L));
        return new EatInOrder(orderLineItems, UUID.randomUUID());
    }
}
