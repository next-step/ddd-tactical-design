package kitchenpos.menus.tobe.domain.fixture;

import kitchenpos.commons.tobe.domain.model.DisplayedName;
import kitchenpos.commons.tobe.domain.model.Price;
import kitchenpos.commons.tobe.domain.model.Quantity;
import kitchenpos.commons.tobe.domain.service.Validator;
import kitchenpos.menus.tobe.domain.model.Menu;
import kitchenpos.menus.tobe.domain.model.MenuProduct;
import kitchenpos.menus.tobe.domain.model.MenuProducts;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.UUID;

import static kitchenpos.menus.tobe.domain.fixture.MenuProductFixture.MENU_PRODUCT_WITH_PRICE_AND_QUANTITY;
import static kitchenpos.menus.tobe.domain.fixture.MenuProductsFixture.MENU_PRODUCTS_WITH_MENU_PRODUCT;

public class MenuFixture {

    private static final Validator<Menu> fakeMenuCreateValidator = menu -> {
    };

    public static Menu MENU_WITH_ALL_FIELDS(final UUID id,
                                            final String name,
                                            final BigDecimal price,
                                            final MenuProducts menuProducts,
                                            final UUID menuGroupId,
                                            final boolean displayed,
                                            final Validator<Menu> validator) {
        return new Menu(
                id,
                new DisplayedName(name),
                new Price(price),
                menuProducts,
                menuGroupId,
                displayed,
                validator
        );
    }

    public static Menu MENU_WITH_MENU_GROUP_ID(final UUID menuGroupId, Validator<Menu> validator) {
        final BigDecimal price = BigDecimal.valueOf(16_000L);

        return MENU_WITH_ALL_FIELDS(
                UUID.randomUUID(),
                "후라이드치킨",
                price,
                MENU_PRODUCTS_WITH_MENU_PRODUCT(MENU_PRODUCT_WITH_PRICE_AND_QUANTITY(price, 1L)),
                menuGroupId,
                true,
                validator
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
                displayed,
                fakeMenuCreateValidator
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
                displayed,
                fakeMenuCreateValidator
        );
    }

    public static Menu MENU_WITH_ID(final UUID id) {
        return new Menu(
                id,
                new DisplayedName("후라이드"),
                new Price(16_000L),
                new MenuProducts(Collections.singletonList(
                        new MenuProduct(UUID.randomUUID(), UUID.randomUUID(), new Price(16_000L), new Quantity(1L))
                )),
                UUID.randomUUID(),
                true,
                fakeMenuCreateValidator
        );
    }

    public static Menu MENU_WITH_PRICE(final long price) {
        return new Menu(
                UUID.randomUUID(),
                new DisplayedName("후라이드"),
                new Price(price),
                new MenuProducts(Collections.singletonList(
                        new MenuProduct(UUID.randomUUID(), UUID.randomUUID(), new Price(price), new Quantity(1L))
                )),
                UUID.randomUUID(),
                true,
                fakeMenuCreateValidator
        );
    }
}
