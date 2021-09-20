package kitchenpos.menus.tobe.domain.menu.fixture;

import kitchenpos.common.domain.DisplayedName;
import kitchenpos.common.domain.Price;
import kitchenpos.common.domain.fixture.DisplayedNameFixture;
import kitchenpos.common.domain.fixture.PriceFixture;
import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.menus.tobe.domain.menu.MenuProducts;
import kitchenpos.menus.tobe.domain.menugroup.MenuGroup;
import kitchenpos.menus.tobe.domain.menugroup.fixture.MenuGroupFixture;
import kitchenpos.products.tobe.domain.fixture.ProductFixture;

public class MenuFixture {
	public static Menu 전시_메뉴() {
		DisplayedName name = DisplayedNameFixture.이름("강정+양념");
		Price price = PriceFixture.가격(19_000L);
		MenuProducts menuProducts = MenuProductsFixture.메뉴상품들(
			MenuProductFixture.메뉴상품(ProductFixture.상품("강정치킨", 17_000L), 1),
			MenuProductFixture.메뉴상품(ProductFixture.상품("양념치킨", 18_000L), 2)
		);
		MenuGroup menuGroup = MenuGroupFixture.메뉴그룹("추천메뉴");
		return new Menu(name, price, true, menuProducts, menuGroup);
	}

	public static Menu 전시_메뉴(Price price) {
		DisplayedName name = DisplayedNameFixture.이름("강정+양념");
		MenuProducts menuProducts = MenuProductsFixture.메뉴상품들(
			MenuProductFixture.메뉴상품(ProductFixture.상품("강정치킨", 17_000L), 1),
			MenuProductFixture.메뉴상품(ProductFixture.상품("양념치킨", 18_000L), 2)
		);
		MenuGroup menuGroup = MenuGroupFixture.메뉴그룹("추천메뉴");
		return new Menu(name, price, true, menuProducts, menuGroup);
	}

	public static Menu 전시_메뉴(Price price, MenuProducts menuProducts) {
		DisplayedName name = DisplayedNameFixture.이름("강정+양념");
		MenuGroup menuGroup = MenuGroupFixture.메뉴그룹("추천메뉴");
		return new Menu(name, price, true, menuProducts, menuGroup);
	}

	public static Menu 숨김_메뉴() {
		DisplayedName name = DisplayedNameFixture.이름("강정+양념");
		Price price = PriceFixture.가격(19_000L);
		MenuProducts menuProducts = MenuProductsFixture.메뉴상품들(
			MenuProductFixture.메뉴상품(ProductFixture.상품("강정치킨", 17_000L), 1),
			MenuProductFixture.메뉴상품(ProductFixture.상품("양념치킨", 18_000L), 2)
		);
		MenuGroup menuGroup = MenuGroupFixture.메뉴그룹("추천메뉴");
		return new Menu(name, price, false, menuProducts, menuGroup);
	}
}
