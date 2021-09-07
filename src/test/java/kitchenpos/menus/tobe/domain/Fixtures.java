package kitchenpos.menus.tobe.domain;

import kitchenpos.commons.tobe.domain.DisplayedName;
import kitchenpos.commons.tobe.domain.Price;
import kitchenpos.commons.tobe.domain.Quantity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.UUID;

public class Fixtures {

    public static MenuGroup MENU_GROUP_WITH_ALL(final UUID id, final String name) {
        return new MenuGroup(id, new DisplayedName(name));
    }

    public static MenuGroup MENU_GROUP_WITH_ID(final UUID id) {
        return MENU_GROUP_WITH_ALL(id, "추천메뉴");
    }

    public static MenuGroup MENU_GROUP_WITH_NAME(final String name) {
        return MENU_GROUP_WITH_ALL(UUID.randomUUID(), name);
    }

    public static Product PRODUCT_WITH_ALL(final UUID id, final BigDecimal price) {
        return new Product(id, new Price(price));
    }

    public static Product DEFAULT_PRODUCT() {
        return PRODUCT_WITH_ALL(UUID.randomUUID(), BigDecimal.valueOf(16_000L));
    }

    public static Product PRODUCT_WITH_ID(final UUID id) {
        return PRODUCT_WITH_ALL(id, BigDecimal.valueOf(16_000L));
    }

    public static Product PRODUCT_WITH_PRICE(final BigDecimal price) {
        return PRODUCT_WITH_ALL(UUID.randomUUID(), price);
    }

    public static MenuProduct MENU_PRODUCT_WITH_ALL(final Product product, final long quantity) {
        return new MenuProduct(product, new Quantity(quantity));
    }

    public static MenuProducts MENU_PRODUCTS_WITH_MENU_PRODUCT(final MenuProduct... menuProducts) {
        return new MenuProducts(Arrays.asList(menuProducts));
    }

    public static Menu MENU_WITH_PRICE_AND_ONLY_ONE_PRODUCT(final BigDecimal price, final Product product) {
        return new Menu(
                UUID.randomUUID(),
                new DisplayedName("단일상품메뉴"),
                new Price(price),
                MENU_PRODUCTS_WITH_MENU_PRODUCT(new MenuProduct(product, new Quantity(1L))),
                UUID.randomUUID(),
                true
        );
    }

    public static Menu DEFAULT_MENU() {
        final Product product = DEFAULT_PRODUCT();
        return new Menu(
                UUID.randomUUID(),
                new DisplayedName("기본메뉴"),
                new Price(product.getPrice()),
                MENU_PRODUCTS_WITH_MENU_PRODUCT(new MenuProduct(product, new Quantity(1L))),
                UUID.randomUUID(),
                true
        );
    }

    public static Menu HIDED_MENU() {
        return new Menu(
                UUID.randomUUID(),
                new DisplayedName("숨겨진메뉴"),
                new Price(BigDecimal.valueOf(1_500L)),
                MENU_PRODUCTS_WITH_MENU_PRODUCT(
                        new MenuProduct(
                                PRODUCT_WITH_PRICE(BigDecimal.valueOf(1_000L)),
                                new Quantity(1L)
                        )
                ),
                UUID.randomUUID(),
                false
        );
    }
}
