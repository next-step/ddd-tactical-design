package kitchenpos.menus.tobe.domain;

import java.util.List;
import java.util.Objects;

import static kitchenpos.menus.tobe.domain.Price.ZERO;

public class MenuProducts {
	private final List<MenuProduct> menuProducts;

	public MenuProducts(List<MenuProduct> menuProducts) {
		if (menuProducts == null || menuProducts.isEmpty()) {
			throw new IllegalArgumentException("MenuProduct가 1개 이상 존재해야 합니다. products: " + menuProducts);
		}

		if (hasDuplicateMenuProduct(menuProducts)) {
			throw new IllegalArgumentException("동등한 MenuProduct가 존재할 수 없습니다.");
		}
		
		this.menuProducts = menuProducts;
	}

	private boolean hasDuplicateMenuProduct(List<MenuProduct> menuProducts) {
		return menuProducts.size() != menuProducts.stream().distinct().count();
	}

	public Price calculatePrice() {
		return menuProducts.stream()
				.map(MenuProduct::calculatePrice)
				.reduce(ZERO, Price::add);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final MenuProducts that = (MenuProducts) o;
		return Objects.equals(menuProducts, that.menuProducts);
	}

	@Override
	public int hashCode() {
		return Objects.hash(menuProducts);
	}
}
