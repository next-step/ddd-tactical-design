package kitchenpos.menus.tobe.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import kitchenpos.core.constant.ExceptionMessages;
import kitchenpos.core.constant.UbiquitousLanguages;
import kitchenpos.fixtures.MenuFixtures;

class MenuProductsTest {

	@DisplayName("메뉴 상품 목록은 추가하려는 값에 ")
	@Nested
	class CreateTest {

		@DisplayName("이상이 없다면 정상적으로 생성된다.")
		@Test
		void create() {
			// given
			MenuProduct menuProduct1 = MenuFixtures.menuProduct();
			MenuProduct menuProduct2 = MenuFixtures.menuProduct();
			BigDecimal expected = menuProduct1.getTotalPrice().add(menuProduct2.getTotalPrice());

			// when
			MenuProducts menuProducts = new MenuProducts(List.of(menuProduct1, menuProduct2));

			// then
			assertAll(
				() -> assertThat(menuProducts).isNotNull(),
				() -> assertThat(menuProducts.getTotalPrice()).isEqualTo(expected)
			);
		}

		@DisplayName("이상이 있다면 (빈 값이라면) 예외가 발생한다.")
		@Test
		void createFailByEmptyValue() {
			// given
			List<MenuProduct> emptyMenuProducts = Collections.emptyList();

			// when
			assertThatExceptionOfType(IllegalArgumentException.class)
				.isThrownBy(() -> new MenuProducts(emptyMenuProducts))
				.withMessage(String.format(ExceptionMessages.EMPTY_INVENTORY_TEMPLATE, UbiquitousLanguages.MENU_PRODUCTS));
		}
	}

	@DisplayName("메뉴 상품 목록의 총 가격을 반환한다.")
	@Test
	void getTotalPrice() {
		// given
		MenuProducts menuProducts = new MenuProducts(
			List.of(
				MenuFixtures.menuProduct(),
				MenuFixtures.menuProduct()
			));

		// when
		BigDecimal totalPrice = menuProducts.getTotalPrice();

		// then
		assertThat(totalPrice)
			.isEqualTo(BigDecimal.valueOf(MenuFixtures.MENU_PRODUCT_QUANTITY * MenuFixtures.MENU_PRODUCT_PRICE * 2));
	}
}
