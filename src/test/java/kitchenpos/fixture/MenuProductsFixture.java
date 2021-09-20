package kitchenpos.fixture;

import static kitchenpos.fixture.MenuProductFixture.CHEAP_MENU_PRODUCT;
import static kitchenpos.fixture.MenuProductFixture.MENU_PRODUCT1;
import static kitchenpos.fixture.MenuProductFixture.MENU_PRODUCT2;
import static kitchenpos.fixture.MenuProductFixture.WRONG_PRODUCT;

import java.util.Arrays;
import kitchenpos.menus.tobe.domain.model.MenuProducts;

public class MenuProductsFixture {

    public static MenuProducts MENU_PRODUCTS() {
        return new MenuProducts(Arrays.asList(MENU_PRODUCT1(), MENU_PRODUCT2()));
    }

    public static MenuProducts WRONG_PRODUCTS() {
        return new MenuProducts(Arrays.asList(MENU_PRODUCT1(), WRONG_PRODUCT()));
    }

    public static MenuProducts CHEAP_MENU_PRODUCTS() {
        return new MenuProducts(Arrays.asList(CHEAP_MENU_PRODUCT(), CHEAP_MENU_PRODUCT()));
    }

}
