package kitchenpos.fixtures;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import kitchenpos.core.constant.Specs;
import kitchenpos.core.domain.Name;
import kitchenpos.core.domain.Price;
import kitchenpos.core.domain.Quantity;
import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuGroup;
import kitchenpos.menus.tobe.domain.MenuProduct;
import kitchenpos.menus.tobe.domain.MenuProducts;
import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.tobe.domain.ProductNameSpecification;

public final class MenuFixtures {

	public static final long MENU_PRICE = 900_000;
	public static final String MENU_NAME = "글렌피딕 세트";
	public static final String MENU_GROUP_NAME = "싱글몰트 위스키";

	public static final long MENU_PRODUCT_PRICE = 200_000;
	public static final long MENU_PRODUCT_QUANTITY = 3;

	private MenuFixtures() {
	}

	public static Menu menu() {
		return menu(true, menuProducts());
	}

	public static Menu menu(boolean displayed) {
		return menu(displayed, menuProducts());
	}

	public static Menu menu(boolean displayed, MenuProducts menuProducts) {
		return new Menu(
			displayed,
			new Name(MENU_NAME, Specs.Menu.NAME),
			new Price(BigDecimal.valueOf(MENU_PRICE), Specs.Product.PRICE),
			menuGroup(),
			menuProducts
		);
	}

	public static MenuGroup menuGroup() {
		return new MenuGroup(
			new Name(
				MENU_GROUP_NAME,
				new ProductNameSpecification(new FakePurgomalumClient())
			)
		);
	}

	public static MenuProduct menuProduct() {
		return menuProduct(MENU_PRODUCT_PRICE, MENU_PRODUCT_QUANTITY);
	}

	public static MenuProduct menuProduct(long price, long quantity) {
		return new MenuProduct(
			UUID.randomUUID(),
			UUID.randomUUID(),
			new Price(new BigDecimal(price), Specs.Product.PRICE),
			new Quantity(quantity, Specs.Menu.QUANTITY)
		);
	}

	public static MenuProducts menuProducts() {
		return new MenuProducts(List.of(menuProduct(), menuProduct()));
	}
}
