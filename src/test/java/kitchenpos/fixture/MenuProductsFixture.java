package kitchenpos.fixture;

import static kitchenpos.fixture.MenuProductFixture.CHEAP_MENU_PRODUCT;
import static kitchenpos.fixture.MenuProductFixture.MENU_PRODUCT1;
import static kitchenpos.fixture.MenuProductFixture.MENU_PRODUCT2;
import static kitchenpos.fixture.MenuProductFixture.WRONG_PRODUCT;

import java.util.Arrays;
import java.util.List;
import kitchenpos.menus.tobe.domain.model.MenuProduct;

public class MenuProductsFixture {

    public static List<MenuProduct> MENU_PRODUCTS() {
        return Arrays.asList(MENU_PRODUCT1(), MENU_PRODUCT2());
    }

    public static List<MenuProduct> WRONG_PRODUCTS() {
        return Arrays.asList(MENU_PRODUCT1(), WRONG_PRODUCT());
    }

    public static List<MenuProduct> CHEAP_MENU_PRODUCTS() {
        return Arrays.asList(CHEAP_MENU_PRODUCT(), CHEAP_MENU_PRODUCT());
    }

}
