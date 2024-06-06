package kitchenpos;

import kitchenpos.eatinorders.todo.domain.orders.EatInOrder;
import kitchenpos.eatinorders.todo.domain.orders.EatInOrderStatus;
import kitchenpos.eatinorders.todo.domain.ordertables.NumberOfGuests;
import kitchenpos.eatinorders.todo.domain.ordertables.OrderTable;
import kitchenpos.eatinorders.todo.domain.ordertables.OrderTableName;
import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.menus.tobe.domain.menu.MenuName;
import kitchenpos.support.domain.MenuPrice;
import kitchenpos.menus.tobe.domain.menu.MenuProduct;
import kitchenpos.menus.tobe.domain.menu.MenuProducts;
import kitchenpos.menus.tobe.domain.menugroup.MenuGroup;
import kitchenpos.menus.tobe.domain.menugroup.MenuGroupName;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductName;
import kitchenpos.products.tobe.domain.ProfanityChecker;
import kitchenpos.support.domain.OrderLineItem;
import kitchenpos.support.domain.ProductPrice;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class Fixtures {
    public static final UUID INVALID_ID = new UUID(0L, 0L);

    public static Menu menu() {
        return menu(19_000L, true, menuProduct());
    }

    public static Menu menu(final long price, final MenuProduct... menuProducts) {
        return menu(price, false, menuProducts);
    }

    public static Menu menu(final long price, final boolean displayed, final MenuProduct... menuProducts) {
        return new Menu(
                MenuName.from("후라이드+후라이드"),
                MenuPrice.from(price),
                menuGroup(),
                displayed,
                MenuProducts.from(Arrays.asList(menuProducts))
        );
    }

    public static MenuGroup menuGroup() {
        return menuGroup("두마리메뉴");
    }

    public static MenuGroup menuGroup(final String name) {
        return MenuGroup.from(MenuGroupName.from(name));
    }

    public static MenuProduct menuProduct() {
        return menuProduct(product(), 2L);
    }

    public static MenuProduct menuProduct(final Product product, final long quantity) {
        return new MenuProduct(new Random().nextLong(), product.getId(), ProductPrice.from(product.getPrice()), quantity);
    }

    public static EatInOrder order(final EatInOrderStatus status) {
        return order(status, OrderTable.from(OrderTableName.from("1번")));
    }

    public static EatInOrder order(final EatInOrderStatus status, final OrderTable orderTable) {
        return EatInOrder.of(status, List.of(orderLineItem()), orderTable.getId());
    }

    public static OrderLineItem orderLineItem() {
        return new OrderLineItem(new Random().nextLong(), menu().getId(), menu().getPrice(), 0);
    }

    public static OrderTable orderTable() {
        return orderTable(false, 0);
    }

    public static OrderTable orderTable(final boolean occupied, final int numberOfGuests) {
        return OrderTable.from(OrderTableName.from("1번"), NumberOfGuests.from(numberOfGuests), occupied);
    }

    public static Product product() {
        return product("후라이드", 16_000L);
    }

    public static Product product(final String name, final long price) {
        return Product.of(ProductName.from(name), ProductPrice.from(price));
    }

    public static Product product(final String name, final long price, ProfanityChecker profanityChecker) {
        return Product.of(ProductName.from(name, profanityChecker), ProductPrice.from(price));
    }
}
