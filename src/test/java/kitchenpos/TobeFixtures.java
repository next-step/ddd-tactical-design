package kitchenpos;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menugroups.tobe.domain.MenuGroup;
import kitchenpos.menus.tobe.domain.MenuProduct;
import kitchenpos.products.tobe.application.dto.ProductCreationRequest;
import kitchenpos.products.tobe.domain.Product;

public class TobeFixtures {

	public static final UUID INVALID_ID = new UUID(0L, 0L);

	public static Menu menu() {
		return menu(UUID.randomUUID(), 19_000L, true, menuProduct());
	}

	public static Menu menu(UUID id) {
		return menu(id, 19_000L, true, menuProduct());
	}

	public static Menu menu(final long price, final MenuProduct... menuProducts) {
		return menu(UUID.randomUUID(), price, true, menuProducts);
	}

	public static Menu menu(final long price, final boolean displayed, final MenuProduct... menuProducts) {
		return menu(UUID.randomUUID(), price, displayed, menuProducts);
	}

	public static Menu menu(final UUID id, final long price, final MenuProduct... menuProducts) {
		return menu(id, price, true, menuProducts);
	}

	public static Menu menu(final UUID id, final long price, final boolean displayed, final MenuProduct... menuProducts) {
		return new Menu(
			id,
			"후라이드+후라이드",
			BigDecimal.valueOf(price),
			menuGroup(),
			List.of(menuProducts),
			displayed
		);
	}

	public static MenuGroup menuGroup() {
		return menuGroup("두마리메뉴");
	}

	public static MenuGroup menuGroup(final String name) {
		return new MenuGroup(UUID.randomUUID(), name);
	}

	public static MenuProduct menuProduct() {
		return new MenuProduct(new Random().nextLong(), product(), 2L);
	}

	public static MenuProduct menuProduct(final Product product, final long quantity) {
		return new MenuProduct(new Random().nextLong(), product, quantity);
	}

	public static Product product() {
		return product("후라이드", 16_000L);
	}

	public static Product product(final String name, final long price) {
		return new Product(name, BigDecimal.valueOf(price));
	}

	public static ProductCreationRequest productCreationRequest(final String name, final long price) {
		return new ProductCreationRequest(name, BigDecimal.valueOf(price));
	}
}

