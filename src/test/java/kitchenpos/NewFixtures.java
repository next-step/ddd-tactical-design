package kitchenpos;

import kitchenpos.common.domain.DisplayedName;
import kitchenpos.common.domain.Price;
import kitchenpos.eatinorders.domain.OrderStatus;
import kitchenpos.eatinorders.domain.OrderType;
import kitchenpos.eatinorders.tobe.domain.*;
import kitchenpos.menus.tobe.domain.*;
import kitchenpos.products.application.FakeDisplayNameChecker;
import kitchenpos.products.domain.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

public class NewFixtures {
    public static final UUID INVALID_ID = new UUID(0L, 0L);

    public static NewMenu menu(final long price, final NewMenuProduct... newMenuProducts) {
        return menu(price, false, newMenuProducts);
    }

    public static NewMenu menu(final long price, final boolean displayed, final NewMenuProduct... newMenuProducts) {
        return NewMenu.create(
                UUID.randomUUID(),
                menuGroup().getId(),
                DisplayedName.of("후라이드+후라이드", new FakeDisplayNameChecker()),
                Price.of(BigDecimal.valueOf(price)),
                MenuProducts.of(Arrays.asList(newMenuProducts)),
                displayed);
    }

    public static NewMenuGroup menuGroup() {
        return menuGroup("두마리메뉴");
    }

    public static NewMenuGroup menuGroup(final String name) {
        return NewMenuGroup.create(UUID.randomUUID(), name);
    }

    public static NewMenuGroup tobeMenuGroup(final String name) {
        return NewMenuGroup.create(UUID.randomUUID(), name);
    }

    public static NewMenuProduct menuProduct(final NewProduct product, final long quantity) {
        return NewMenuProduct.create(new Random().nextLong(), product, quantity);
    }

    public static NewMenuProduct menuProduct(final Product product, final long quantity) {
        return NewMenuProduct.create(
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
        final EatInOrder order = new EatInOrder();
        order.setId(UUID.randomUUID());
        order.setType(OrderType.EAT_IN);
        order.setStatus(status);
        order.setOrderDateTime(LocalDateTime.of(2020, 1, 1, 12, 0));
        order.setOrderLineItems(Arrays.asList(orderLineItem()));
        order.setOrderTable(orderTable);
        return order;
    }

    public static EatInOrderLineItem orderLineItem() {
        return EatInOrderLineItem.create(
                new Random().nextLong(), eatInMenu(), 1L, Price.of(BigDecimal.valueOf(10_000L)));
    }

    private static EatInMenu eatInMenu() {
        return EatInMenu.create(UUID.randomUUID(), Price.of(BigDecimal.valueOf(10_000L)), true);
    }
}
