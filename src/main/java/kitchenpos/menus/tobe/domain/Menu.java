package kitchenpos.menus.tobe.domain;

import java.util.Objects;
import java.util.UUID;

public class Menu {
	private final UUID id;
	private final UUID menuGroupId;
	private final DisplayedName displayedName;
	private Price price;
	private final MenuProducts menuProducts;
	private boolean displayed;

	public static Menu create(UUID id, UUID menuGroupId, DisplayedName displayedName, Price price, MenuProducts menuProducts) {
		return new Menu(id, menuGroupId, displayedName, price, menuProducts, true);
	}
	
	private Menu(UUID id, UUID menuGroupId, DisplayedName displayedName, Price price, MenuProducts menuProducts, boolean displayed) {
		if (price.isHigherThan(menuProducts.calculatePrice())) {
			throw new IllegalArgumentException("메뉴의 가격은 메뉴에 속한 상품 금액의 합보다 작거나 같아야 합니다.");
		}

		this.id = id;
		this.menuGroupId= menuGroupId;
		this.displayedName = displayedName;
		this.price = price;
		this.menuProducts = menuProducts;
		this.displayed = displayed;
	}

	public void changePrice(Price price) {
		if (price.isHigherThan(menuProducts.calculatePrice())) {
			throw new IllegalArgumentException("메뉴의 가격은 메뉴에 속한 상품 금액의 합보다 작거나 같아야 합니다.");
		}
		this.price = price;
	}

	public void display() {
		if (this.price.isHigherThan(menuProducts.calculatePrice())) {
			throw new IllegalStateException("메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 높아 메뉴를 노출할 수 없습니다. id: " + id);
		}
		this.displayed = true;
	}

	public void hide() {
		this.displayed = false;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final Menu menu = (Menu) o;
		return Objects.equals(id, menu.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
