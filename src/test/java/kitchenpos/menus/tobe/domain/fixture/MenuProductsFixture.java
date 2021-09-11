package kitchenpos.menus.tobe.domain.fixture;

import kitchenpos.menus.tobe.domain.model.MenuProduct;
import kitchenpos.menus.tobe.domain.model.MenuProducts;

import java.util.Arrays;
import java.util.List;

public class MenuProductsFixture {

    public static MenuProducts MENU_PRODUCTS_WITH_MENU_PRODUCT(final MenuProduct... menuProducts) {
        return new MenuProducts(Arrays.asList(menuProducts));
    }

    public static MenuProducts MENU_PRODUCTS_WITH_MENU_PRODUCT(final List<MenuProduct> menuProducts) {
        return new MenuProducts(menuProducts);
    }
}
