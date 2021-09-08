package kitchenpos.menus.tobe.domain.fixtures;

import kitchenpos.commons.tobe.domain.Price;
import kitchenpos.commons.tobe.domain.Quantity;
import kitchenpos.menus.tobe.domain.MenuProduct;

import java.math.BigDecimal;
import java.util.UUID;

public class MenuProductFixture {

    public static MenuProduct MENU_PRODUCT_WITH_ALL_FIELDS(
            final UUID menuId,
            final UUID productId,
            final BigDecimal price,
            final long quantity) {
        return new MenuProduct(menuId, productId, new Price(price), new Quantity(quantity));
    }

    public static MenuProduct MENU_PRODUCT_WITH_MENU_ID(final UUID menuId) {
        return MENU_PRODUCT_WITH_ALL_FIELDS(menuId, UUID.randomUUID(), BigDecimal.valueOf(16_000L), 1L);
    }

    public static MenuProduct MENU_PRODUCT_WITH_PRODUCT_ID(final UUID productId) {
        return MENU_PRODUCT_WITH_ALL_FIELDS(UUID.randomUUID(), productId, BigDecimal.valueOf(16_000L), 1L);
    }

    public static MenuProduct MENU_PRODUCT_WITH_PRICE_AND_QUANTITY(final BigDecimal price, final long quantity) {
        return MENU_PRODUCT_WITH_ALL_FIELDS(UUID.randomUUID(), UUID.randomUUID(), price, quantity);
    }
}
