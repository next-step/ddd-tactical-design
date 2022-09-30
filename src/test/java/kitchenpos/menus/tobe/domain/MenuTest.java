package kitchenpos.menus.tobe.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import kitchenpos.core.constant.ExceptionMessages;
import kitchenpos.core.constant.Specs;
import kitchenpos.core.domain.Name;
import kitchenpos.core.domain.Price;

class MenuTest {

	private Menu menu;

	@BeforeEach
	void setUp() {
		menu = MenuFixtures.menu();
	}

	@DisplayName("메뉴 생성 시 ")
	@Nested
	class CreateTest {

		@DisplayName("메뉴 가격 조건에 이상이 없다면 정상적으로 생성된다.")
		@Test
		void create() {
			// given
			Name name = new Name(MenuFixtures.MENU_NAME, Specs.Menu.NAME);
			Price price = new Price(BigDecimal.valueOf(MenuFixtures.MENU_PRICE), Specs.Product.PRICE);

			// when, then
			assertDoesNotThrow(() -> new Menu(true, name, price, MenuFixtures.menuGroup(), MenuFixtures.menuProducts()));
		}

		@DisplayName("메뉴 가격 조건에 이상이 있다면 예외가 발생한다.")
		@Test
		void createFailByMenuPricePolicy() {
			// given
			Name name = new Name(MenuFixtures.MENU_NAME, Specs.Menu.NAME);
			Price price = new Price(BigDecimal.valueOf(Long.MAX_VALUE), Specs.Product.PRICE);

			// when, then
			assertThatExceptionOfType(IllegalArgumentException.class)
				.isThrownBy(() -> new Menu(true, name, price, MenuFixtures.menuGroup(), MenuFixtures.menuProducts()))
				.withMessage(ExceptionMessages.Menu.INVALID_PRICE);
		}
	}

	@DisplayName("메뉴 가격 변경 시 ")
	@Nested
	class ChangePriceTest {

		@DisplayName("가격에 문제가 없다면 정상적으로 변경된다.")
		@Test
		void changePrice() {
			// given
			BigDecimal originalPrice = BigDecimal.valueOf(MenuFixtures.MENU_PRICE);
			BigDecimal newPrice = originalPrice.divide(BigDecimal.valueOf(2), RoundingMode.CEILING);

			// when
			menu.changePrice(new Price(newPrice, Specs.Menu.PRICE));

			// then
			assertThat(menu.getPrice()).isEqualTo(newPrice);
		}

		@DisplayName("메뉴 가격이 메뉴에 속한 상품들의 금액 총합보다 크다면 예외가 발생한다.")
		@Test
		void changePriceFailByMenuPricePolicy() {
			// given
			Price newPrice = new Price(BigDecimal.valueOf(Long.MAX_VALUE), Specs.Menu.PRICE);

			// when
			assertThatExceptionOfType(IllegalArgumentException.class)
				.isThrownBy(() -> menu.changePrice(newPrice))
				.withMessage(ExceptionMessages.Menu.INVALID_PRICE);
		}
	}

	@DisplayName("메뉴의 진열 상태 변경 시 ")
	@Nested
	class DisplayTest {

		@DisplayName("메뉴의 가격 조건에 문제 없다면 진열로 변경할 수 있다.")
		@Test
		void display() {
			// given
			Menu menu = MenuFixtures.menu(false);

			// when
			menu.display();

			// then
			assertThat(menu.isDisplayed()).isTrue();
		}

		@DisplayName("메뉴의 가격이 메뉴에 속한 상품 금액의 총합보다 클 경우 변경할 수 없다.")
		@Test
		void displayFailByMenuPricePolicy() {
			// given
			MenuProduct menuProduct = MenuFixtures.menuProduct();
			Menu menu = MenuFixtures.menu(
				false,
				new MenuProducts(List.of(menuProduct, menuProduct, menuProduct))
			);

			// when
			menuProduct.changePrice(new Price(BigDecimal.ZERO, Specs.Product.PRICE));

			// then
			assertThatExceptionOfType(IllegalArgumentException.class)
				.isThrownBy(menu::display)
				.withMessage(ExceptionMessages.Menu.INVALID_PRICE);

			assertThat(menu.isDisplayed()).isFalse();
		}

		@DisplayName("미진열로 변경에는 특정 조건 없이 변경 가능하다.")
		@Test
		void hide() {
			// when
			menu.hide();

			// then
			assertThat(menu.isDisplayed()).isFalse();
		}
	}
}
