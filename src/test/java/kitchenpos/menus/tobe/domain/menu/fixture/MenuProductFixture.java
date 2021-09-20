package kitchenpos.menus.tobe.domain.menu.fixture;

import kitchenpos.menus.tobe.domain.menu.MenuProduct;
import kitchenpos.menus.tobe.domain.menu.Quantity;
import kitchenpos.products.tobe.domain.Product;

public class MenuProductFixture {
	public static MenuProduct 메뉴상품(Product product, long quantity) {
		return new MenuProduct(product, new Quantity(quantity));
	}
}
