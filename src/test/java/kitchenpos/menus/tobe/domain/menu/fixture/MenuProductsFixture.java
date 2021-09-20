package kitchenpos.menus.tobe.domain.menu.fixture;

import java.util.Arrays;

import kitchenpos.menus.tobe.domain.menu.MenuProduct;
import kitchenpos.menus.tobe.domain.menu.MenuProducts;

public class MenuProductsFixture {
	public static MenuProducts 메뉴상품들(MenuProduct... menuProducts) {
		return new MenuProducts(Arrays.asList(menuProducts));
	}
}
