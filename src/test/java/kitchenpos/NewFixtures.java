package kitchenpos;

import kitchenpos.common.domain.DisplayedName;
import kitchenpos.common.domain.Price;
import kitchenpos.eatinorders.domain.*;
import kitchenpos.menus.domain.*;
import kitchenpos.products.application.FakeDisplayNameChecker;
import kitchenpos.products.domain.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class NewFixtures {
    public static final UUID INVALID_ID = new UUID(0L, 0L);

    public static Menu menu(final long price, final MenuProduct... menuProducts) {
        return menu(price, false, menuProducts);
    }

    public static Menu menu(final long price, final boolean displayed, final MenuProduct... menuProducts) {
        return Menu.create(
                UUID.randomUUID(),
                menuGroup().getId(),
                DisplayedName.of("후라이드+후라이드", new FakeDisplayNameChecker()),
                Price.of(BigDecimal.valueOf(price)),
                MenuProducts.of(Arrays.asList(menuProducts)),
                displayed);
    }

    public static MenuGroup menuGroup() {
        return menuGroup("두마리메뉴");
    }

    public static MenuGroup menuGroup(final String name) {
        return MenuGroup.create(UUID.randomUUID(), name);
    }

    public static MenuGroup tobeMenuGroup(final String name) {
        return MenuGroup.create(UUID.randomUUID(), name);
    }

    public static MenuProduct menuProduct(final NewProduct product, final long quantity) {
        return MenuProduct.create(new Random().nextLong(), product, quantity);
    }

    public static MenuProduct menuProduct(final Product product, final long quantity) {
        return MenuProduct.create(
                new Random().nextLong(),
                NewProduct.create(product.getId(), Price.of(product.getPrice())),
                quantity);
    }

    public static Product product(final String name, final long price) {
        return Product.create(UUID.randomUUID(), BigDecimal.valueOf(price), name, new FakeDisplayNameChecker());
    }

    public static NewProduct newProduct(final long price) {
        return NewProduct.create(UUID.randomUUID(), Price.of(BigDecimal.valueOf(price)));
    }

    public static EatInOrderTable orderTable(final boolean occupied, final int numberOfGuests) {
        return EatInOrderTable.create(
                UUID.randomUUID(),
                OrderTableName.create("1번"),
                NumberOfGuests.create(numberOfGuests),
                occupied
        );
    }

    public static EatInOrder order(final OrderStatus status, final EatInOrderTable orderTable) {
        return EatInOrder.create(
                UUID.randomUUID(),
                status,
                LocalDateTime.now(),
                eatInOrderLineItems(Arrays.asList(eatInOrderLineItem())),
                orderTable
        );
    }

    public static EatInOrder order(final OrderStatus status) {
        return EatInOrder.create(
                UUID.randomUUID(),
                status,
                LocalDateTime.now(),
                eatInOrderLineItems(Arrays.asList(eatInOrderLineItem())),
                orderTable(true, 5)
        );
    }

    private static EatInOrderLineItems eatInOrderLineItems(List<EatInOrderLineItem> eatInOrderLineItems) {
        return EatInOrderLineItems.create(eatInOrderLineItems);
    }

    public static EatInOrderLineItem eatInOrderLineItem() {
        return EatInOrderLineItem.create(
                new Random().nextLong(), eatInMenu(), 1L, Price.of(BigDecimal.valueOf(10_000L)));
    }

    public static EatInMenu eatInMenu() {
        return EatInMenu.create(UUID.randomUUID(), Price.of(BigDecimal.valueOf(10_000L)), true);
    }

    public static EatInMenu eatInMenu(Long price, boolean displayed) {
        return EatInMenu.create(UUID.randomUUID(), Price.of(BigDecimal.valueOf(price)), displayed);
    }


}
