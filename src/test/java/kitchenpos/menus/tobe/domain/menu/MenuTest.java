package kitchenpos.menus.tobe.domain.menu;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import kitchenpos.common.domain.DisplayedName;
import kitchenpos.common.domain.Price;
import kitchenpos.menus.tobe.domain.menu.fixture.MenuProductFixture;
import kitchenpos.menus.tobe.domain.menu.fixture.MenuProductsFixture;
import kitchenpos.menus.tobe.domain.menugroup.MenuGroup;
import kitchenpos.menus.tobe.domain.menugroup.fixture.MenuGroupFixture;
import kitchenpos.products.tobe.domain.fixture.ProductFixture;

public class MenuTest {
	@ParameterizedTest
	@ValueSource(strings = {"10000", "53000"})
	void 메뉴를_생성할_수_있다(long value) {
		// given
		DisplayedName name = new DisplayedName("강정+양념", text -> false);
		Price price = new Price(new BigDecimal(value));
		boolean displayed = true;
		MenuProducts menuProducts = MenuProductsFixture.메뉴상품들(
			MenuProductFixture.메뉴상품(ProductFixture.상품("강정치킨", 17_000L), 1),
			MenuProductFixture.메뉴상품(ProductFixture.상품("양념치킨", 18_000L), 2)
		);
		MenuGroup menuGroup = MenuGroupFixture.메뉴그룹("추천메뉴");

		// when
		Menu menu = new Menu(name, price, displayed, menuProducts, menuGroup);

		// then
		assertAll(
			() -> assertThat(menu).isNotNull(),
			() -> assertThat(menu.getId()).isNotNull(),
			() -> assertThat(menu.getName()).isEqualTo(name),
			() -> assertThat(menu.getPrice()).isEqualTo(price),
			() -> assertThat(menu.isDisplayed()).isEqualTo(displayed),
			() -> assertThat(menu.getMenuGroup()).isEqualTo(menuGroup),
			() -> assertThat(menu.getMenuProducts()).isNotNull(),
			() -> assertThat(menu.getMenuProducts().getValues()).isNotEmpty()
		);
	}

	@ParameterizedTest
	@ValueSource(strings = {"54000", "100000"})
	void 메뉴의_가격이_메뉴상품들의_전체_가격보다_크다면_메뉴를_생성할_수_없다(long value) {
		// given
		DisplayedName name = new DisplayedName("강정+양념", text -> false);
		Price price = new Price(new BigDecimal(value));
		boolean displayed = true;
		MenuProducts menuProducts = MenuProductsFixture.메뉴상품들(
			MenuProductFixture.메뉴상품(ProductFixture.상품("강정치킨", 17_000L), 1),
			MenuProductFixture.메뉴상품(ProductFixture.상품("양념치킨", 18_000L), 2)
		);
		MenuGroup menuGroup = MenuGroupFixture.메뉴그룹("추천메뉴");

		// when & then
		assertThatThrownBy(
			() -> new Menu(name, price, displayed, menuProducts, menuGroup)
		).isInstanceOf(IllegalArgumentException.class);
	}
}
