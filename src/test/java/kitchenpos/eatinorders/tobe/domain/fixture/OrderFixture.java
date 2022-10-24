package kitchenpos.eatinorders.tobe.domain.fixture;

import kitchenpos.common.FakeProfanity;
import kitchenpos.common.vo.DisplayedName;
import kitchenpos.common.vo.Price;
import kitchenpos.eatinorders.domain.OrderStatus;
import kitchenpos.eatinorders.domain.OrderType;
import kitchenpos.eatinorders.tobe.domain.Order;
import kitchenpos.eatinorders.tobe.domain.OrderLineItem;
import kitchenpos.eatinorders.tobe.domain.OrderTable;
import kitchenpos.menus.domain.MenuGroup;
import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuProduct;
import kitchenpos.products.tobe.domain.Product;

import java.math.BigDecimal;
import java.util.List;

public class OrderFixture {
    public static Order createEatInOrder() {
        final OrderType orderType = OrderType.EAT_IN;
        final Menu menu = createMenu();
        final List<OrderLineItem> orderLineItems = List.of(new OrderLineItem(menu, -1L));
        final OrderStatus orderStatus = OrderStatus.WAITING;
        final OrderTable orderTable = new OrderTable(5, true);

        return new Order(orderType, orderLineItems, orderStatus, orderTable);
    }

    private static Menu createMenu() {
        final Price price = new Price(BigDecimal.TEN);
        final DisplayedName name = new DisplayedName("치킨 세트", new FakeProfanity());
        final MenuProduct menuProduct = new MenuProduct(2L, new Product(BigDecimal.valueOf(6L)));
        return new Menu(price, name, List.of(menuProduct), new MenuGroup());
    }

}
