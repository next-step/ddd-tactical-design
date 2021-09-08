package kitchenpos.menus.tobe.domain.fixtures;

import kitchenpos.commons.tobe.domain.DisplayedName;
import kitchenpos.commons.tobe.domain.Price;
import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuProduct;
import kitchenpos.menus.tobe.domain.MenuProducts;

import java.math.BigDecimal;
import java.util.UUID;

import static kitchenpos.menus.tobe.domain.fixtures.MenuProductFixture.MENU_PRODUCT_WITH_PRICE_AND_QUANTITY;
import static kitchenpos.menus.tobe.domain.fixtures.MenuProductsFixture.MENU_PRODUCTS_WITH_MENU_PRODUCT;

public class MenuFixture {

    public static Menu MENU_WITH_ALL_FIELDS(final UUID id,
                                            final String name,
                                            final BigDecimal price,
                                            final MenuProducts menuProducts,
                                            final UUID menuGroupId,
                                            final boolean displayed) {
        return new Menu(id, new DisplayedName(name), new Price(price), menuProducts, menuGroupId, displayed);
    }

    public static Menu MENU_WITH_PRICE_AND_MENU_PRODUCTS(final BigDecimal price, final MenuProduct... menuProducts) {
        return MENU_WITH_ALL_FIELDS(
                UUID.randomUUID(),
                "후라이드치킨",
                price,
                MENU_PRODUCTS_WITH_MENU_PRODUCT(menuProducts),
                UUID.randomUUID(),
                true
        );
    }

    public static Menu MENU_WITH_MENU_GROUP_ID(final UUID menuGroupId) {
        final BigDecimal price = BigDecimal.valueOf(16_000L);

        return MENU_WITH_ALL_FIELDS(
                UUID.randomUUID(),
                "후라이드치킨",
                price,
                MENU_PRODUCTS_WITH_MENU_PRODUCT(MENU_PRODUCT_WITH_PRICE_AND_QUANTITY(price, 1L)),
                menuGroupId,
                true
        );
    }

    public static Menu MENU_WITH_DISPLAYED(final boolean displayed) {
        final BigDecimal price = BigDecimal.valueOf(16_000L);
        final long quantity = 1L;

        return MENU_WITH_ALL_FIELDS(
                UUID.randomUUID(),
                "후라이드치킨",
                price,
                MENU_PRODUCTS_WITH_MENU_PRODUCT(MENU_PRODUCT_WITH_PRICE_AND_QUANTITY(price, quantity)),
                UUID.randomUUID(),
                displayed
        );
    }

    public static Menu NOT_DISPLAYED_MENU() {
        final BigDecimal menuPrice = BigDecimal.valueOf(20_000L);
        final BigDecimal menuProductPrice = BigDecimal.valueOf(16_000L);
        final long quantity = 1L;
        final boolean displayed = false;

        return MENU_WITH_ALL_FIELDS(
                UUID.randomUUID(),
                "후라이드치킨",
                menuPrice,
                MENU_PRODUCTS_WITH_MENU_PRODUCT(MENU_PRODUCT_WITH_PRICE_AND_QUANTITY(menuProductPrice, quantity)),
                UUID.randomUUID(),
                displayed
        );
    }
}
