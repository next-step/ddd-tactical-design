package kitchenpos.menus.tobe.domain.fixture;

import kitchenpos.commons.tobe.domain.model.Price;
import kitchenpos.commons.tobe.domain.model.Quantity;
import kitchenpos.menus.tobe.domain.model.MenuProduct;

import java.math.BigDecimal;
import java.util.UUID;

public class MenuProductFixture {

    public static MenuProduct MENU_PRODUCT_WITH_ALL_FIELDS(
            final UUID id,
            final UUID productId,
            final BigDecimal price,
            final long quantity) {
        return new MenuProduct(id, productId, new Price(price), new Quantity(quantity));
    }

    public static MenuProduct MENU_PRODUCT_WITH_PRICE_AND_QUANTITY(final BigDecimal price, final long quantity) {
        return MENU_PRODUCT_WITH_ALL_FIELDS(UUID.randomUUID(), UUID.randomUUID(), price, quantity);
    }
}
