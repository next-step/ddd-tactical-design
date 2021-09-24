package kitchenpos;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;
import kitchenpos.common.domain.DisplayedName;
import kitchenpos.common.domain.FakeProfanities;
import kitchenpos.common.domain.MenuGroupId;
import kitchenpos.common.domain.MenuId;
import kitchenpos.common.domain.Price;
import kitchenpos.common.domain.ProductId;
import kitchenpos.common.domain.Quantity;
import kitchenpos.menus.tobe.domain.menu.Displayed;
import kitchenpos.menus.tobe.domain.menu.FakeMenuValidator;
import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.menus.tobe.domain.menu.MenuProduct;
import kitchenpos.menus.tobe.domain.menu.MenuProductSeq;
import kitchenpos.menus.tobe.domain.menu.MenuProducts;
import kitchenpos.menus.tobe.domain.menugroup.MenuGroup;
import kitchenpos.products.tobe.domain.Product;

public class ToBeFixtures {

    public static final UUID INVALID_ID = new UUID(0L, 0L);

    //

    public static MenuGroup menuGroup() {
        return menuGroup("두마리메뉴");
    }

    public static MenuGroup menuGroup(final String name) {
        return menuGroup(UUID.randomUUID(), name);
    }

    public static MenuGroup menuGroup(final UUID id, final String name) {
        return new MenuGroup(
            new MenuGroupId(id),
            new DisplayedName(name, new FakeProfanities())
        );
    }

    //

    public static Menu menu(
        final long price,
        final MenuProduct... menuProducts
    ) {
        return new Menu(
            new MenuId(UUID.randomUUID()),
            new DisplayedName(
                "랜덤메뉴",
                new FakeProfanities()
            ),
            new Price(BigDecimal.valueOf(price)),
            new MenuGroupId(UUID.randomUUID()),
            new Displayed(true),
            new MenuProducts(Arrays.asList(menuProducts)),
            new FakeMenuValidator()
        );
    }

    public static Menu menu(
        final long price,
        final MenuProducts menuProducts
    ) {
        return new Menu(
            new MenuId(UUID.randomUUID()),
            new DisplayedName(
                "랜덤메뉴",
                new FakeProfanities()
            ),
            new Price(BigDecimal.valueOf(price)),
            new MenuGroupId(UUID.randomUUID()),
            new Displayed(true),
            menuProducts,
            new FakeMenuValidator()
        );
    }

    public static Menu menu(final boolean displayed) {
        return new Menu(
            new MenuId(UUID.randomUUID()),
            new DisplayedName(
                "후라이드+간장치킨",
                new FakeProfanities()
            ),
            new Price(BigDecimal.valueOf(32_000L)),
            new MenuGroupId(UUID.randomUUID()),
            new Displayed(displayed),
            menuProducts(),
            new FakeMenuValidator()
        );
    }

    public static Menu menu() {
        return new Menu(
            new MenuId(UUID.randomUUID()),
            new DisplayedName(
                "후라이드+간장치킨",
                new FakeProfanities()
            ),
            new Price(BigDecimal.valueOf(32_000L)),
            new MenuGroupId(UUID.randomUUID()),
            new Displayed(true),
            menuProducts(),
            new FakeMenuValidator()
        );
    }

    //

    public static MenuProducts menuProducts() {
        final Product product1 = product("후라이드", 16_000L);
        final MenuProduct menuProduct1 = menuProduct(
            product1.getId(),
            product1.getPrice(),
            1L
        );
        final Product product2 = product("후라이드", 16_000L);
        final MenuProduct menuProduct2 = menuProduct(
            product2.getId(),
            product2.getPrice(),
            1L
        );
        return new MenuProducts(Arrays.asList(menuProduct1, menuProduct2));
    }

    public static MenuProduct menuProduct() {
        final Product product = product();
        return menuProduct(product.getId(), product.getPrice(), 2L);
    }

    public static MenuProduct menuProduct(
        final ProductId productId,
        final Price price,
        final long quantity
    ) {
        return menuProduct(
            new Random().nextLong(),
            productId,
            price,
            quantity
        );
    }

    public static MenuProduct menuProduct(
        final Long seq,
        final ProductId productId,
        final Price price,
        final long quantity
    ) {
        return new MenuProduct(
            new MenuProductSeq(seq),
            productId,
            price,
            new Quantity(quantity)
        );
    }

    //

    public static Product product() {
        return product("후라이드", 16_000L);
    }

    public static Product product(final String displayedName, final long price) {
        return product(new ProductId(UUID.randomUUID()), displayedName, price);
    }

    public static Product product(ProductId id, final String displayedName, final long price) {
        return new Product(
            id,
            new DisplayedName(displayedName, new FakeProfanities()),
            new Price(BigDecimal.valueOf(price))
        );
    }
}
