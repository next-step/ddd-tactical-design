package kitchenpos.menus.tobe.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import kitchenpos.core.constant.Specs;
import kitchenpos.core.domain.Price;
import kitchenpos.core.domain.Quantity;

class MenuProductTest {

	private MenuProduct menuProduct;

	@BeforeEach
	void setUp() {
		menuProduct = MenuFixtures.menuProduct();
	}

	@DisplayName("메뉴 상품은 추가하려는 값에 ")
	@Nested
	class CreateTest {

		@DisplayName("이상이 없다면 정상적으로 생성된다.")
		@Test
		void create() {
			// given
			Price price = new Price(BigDecimal.valueOf(1000), Specs.Menu.PRICE);
			Quantity quantity = new Quantity(1, Specs.Menu.QUANTITY);

			// when
			MenuProduct menuProduct = new MenuProduct(
				UUID.randomUUID(),
				UUID.randomUUID(),
				price,
				quantity
			);

			// then
			assertAll(
				() -> assertThat(menuProduct).isNotNull(),
				() -> assertThat(menuProduct.getTotalPrice()).isEqualTo(price.multiply(quantity))
			);
		}
	}

	@DisplayName("값 객체는 검증을 거친 후 생성되므로 ")
	@Nested
	class ChangeTest {

		@DisplayName("가격 변경에는 검증이 필요치 않다.")
		@Test
		void changePrice() {
			// given
			Price price = new Price(BigDecimal.valueOf(1000), Specs.Product.PRICE);
			BigDecimal expected = price.multiply(
				new Quantity(
					MenuFixtures.MENU_PRODUCT_QUANTITY,
					Specs.Product.QUANTITY
				));

			// when
			menuProduct.changePrice(price);

			// then
			assertThat(menuProduct.getTotalPrice())
				.isEqualTo(expected);
		}

		@DisplayName("수량 변경에는 검증이 필요치 않다.")
		@Test
		void changeQuantity() {
			// given
			Quantity quantity = new Quantity(10, Specs.Product.QUANTITY);
			BigDecimal expected = quantity.multiply(
				new Price(
					BigDecimal.valueOf(MenuFixtures.MENU_PRODUCT_PRICE),
					Specs.Product.PRICE
				));

			// when
			menuProduct.changeQuantity(quantity);

			// then
			assertThat(menuProduct.getTotalPrice())
				.isEqualTo(expected);
		}
	}

	@DisplayName("총 금액은 가격과 수량을 곱한 금액을 반환한다.")
	@Test
	void getTotalPrice() {
		// given
		BigDecimal expected = BigDecimal.valueOf(MenuFixtures.MENU_PRODUCT_QUANTITY * MenuFixtures.MENU_PRODUCT_PRICE);

		// when
		BigDecimal totalPrice = menuProduct.getTotalPrice();

		// then
		assertThat(totalPrice)
			.isEqualTo(expected);
	}
}
