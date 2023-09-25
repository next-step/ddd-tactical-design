package kitchenpos;

import kitchenpos.common.domain.OrderLineItem;
import kitchenpos.common.domain.code.OrderType;
import kitchenpos.common.domain.vo.Name;
import kitchenpos.common.domain.vo.Price;
import kitchenpos.eatinorders.domain.order.Order;
import kitchenpos.eatinorders.domain.order.OrderStatus;
import kitchenpos.eatinorders.domain.ordertable.GuestNumber;
import kitchenpos.eatinorders.domain.ordertable.OrderTable;
import kitchenpos.menus.application.FakeMenuPurgomalumClient;
import kitchenpos.menus.domain.menu.Menu;
import kitchenpos.menus.domain.menu.MenuProduct;
import kitchenpos.menus.domain.menu.MenuProducts;
import kitchenpos.menus.domain.menugroup.MenuGroup;
import kitchenpos.menus.domain.menugroup.MenuGroupName;
import kitchenpos.products.application.FakeProductPurgomalumClient;
import kitchenpos.products.domain.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
                menuGroup(),
                Name.of("후라이드+후라이드",
                new FakeMenuPurgomalumClient()),
                Price.of(BigDecimal.valueOf(price)),
                displayed,
                MenuProducts.of(Arrays.asList(menuProducts))
        );
    }

    public static MenuGroup menuGroup() {
        return menuGroup("두마리메뉴");
    }

    public static MenuGroup menuGroup(final String name) {
        return new MenuGroup(MenuGroupName.of(name));
    }

    public static MenuProduct menuProduct() {
        return new MenuProduct(
                new Random().nextLong(),
                product().getId(),
                2L,
                Price.of(product().getPrice())
        );
    }

    public static MenuProduct menuProduct(final Product product, final long quantity) {
        return new MenuProduct(
                new Random().nextLong(),
                product.getId(),
                quantity,
                Price.of(product.getPrice())
        );
    }

    public static Order order(final OrderStatus status) {
        return new Order(
                OrderType.EAT_IN,
                status,
                LocalDateTime.of(2020, 1, 1, 12, 0),
                List.of(orderLineItem()),
                null
        );
    }

    public static Order order(final OrderStatus status, final OrderTable orderTable) {
        return new Order(
                OrderType.EAT_IN,
                status,
                LocalDateTime.of(2020, 1, 1, 12, 0),
                List.of(orderLineItem()),
                orderTable
        );
    }

    public static OrderLineItem orderLineItem() {
        return new OrderLineItem(new Random().nextLong(), 0, menu().getId(), BigDecimal.ZERO);
    }

    public static OrderTable orderTable() {
        return orderTable(false, 0);
    }

    public static OrderTable orderTable(final boolean occupied, final int numberOfGuests) {
        return new OrderTable(
                Name.of("1번"),
                GuestNumber.of(numberOfGuests),
                occupied
        );
    }

    public static Product product() {
        return product("후라이드", 16_000L);
    }

    public static Product product(final String name, final long price) {
        return new Product(
                Name.of(name, new FakeProductPurgomalumClient()),
                Price.of(BigDecimal.valueOf(price))
        );
    }
}
