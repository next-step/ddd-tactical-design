package kitchenpos.fixture;

import static kitchenpos.fixture.ProductFixture.CHEAP_PRODUCT;
import static kitchenpos.fixture.ProductFixture.PRODUCT1;
import static kitchenpos.fixture.ProductFixture.PRODUCT2;

import java.util.UUID;
import kitchenpos.menus.tobe.menu.domain.model.MenuProduct;
import kitchenpos.menus.tobe.menu.domain.model.Quantity;
import kitchenpos.products.tobe.domain.model.ProductPrice;

public class MenuProductFixture {

    private static final Quantity TWO = new Quantity(2L);
    private static final Quantity THREE = new Quantity(3L);

    public static MenuProduct MENU_PRODUCT1() {
        return new MenuProduct(TWO, PRODUCT1().getId(), PRODUCT1().getPrice());
    }

    public static MenuProduct MENU_PRODUCT2() {
        return new MenuProduct(THREE, PRODUCT2().getId(), PRODUCT2().getPrice());
    }

    public static MenuProduct WRONG_PRODUCT() {
        return new MenuProduct(THREE, UUID.randomUUID(), new ProductPrice(0L));
    }

    public static MenuProduct CHEAP_MENU_PRODUCT() {
        return new MenuProduct(TWO, CHEAP_PRODUCT().getId(), CHEAP_PRODUCT().getPrice());
    }

}
