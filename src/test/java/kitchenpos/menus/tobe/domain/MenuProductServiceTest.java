package kitchenpos.menus.tobe.domain;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import kitchenpos.core.constant.ExceptionMessages;
import kitchenpos.core.constant.Specs;
import kitchenpos.core.domain.Price;
import kitchenpos.core.domain.Quantity;

class MenuProductServiceTest {

	private MenuProduct menuProduct;
	private Menu menu;
	private MenuProductService menuProductService;

	@BeforeEach
	void setUp() {
		menuProduct = MenuFixtures.menuProduct();
		menu = MenuFixtures.menu(menuProduct);
		menuProductService = new MenuProductService();
	}

	@DisplayName("메뉴 상품의 가격이나 수량을 변경 시 메뉴 금액 보다 메뉴 상품들의 금액 총합이 ")
	@Nested
	class ChangeTest {

		@DisplayName("크다면 가격을 변경할 수 있다.")
		@Test
		void changePrice() {
			// given
			Price newPrice = new Price(
				BigDecimal.valueOf(Long.MAX_VALUE),
				Specs.Product.PRICE
			);

			// when, then
			assertThatCode(() -> menuProductService.changePrice(menu, menuProduct, newPrice))
				.doesNotThrowAnyException();
		}

		@DisplayName("작다면 가격을 변경할 수 없다.")
		@Test
		void changePriceFailByMenuPricePolicy() {
			// given
			Price newPrice = new Price(
				BigDecimal.valueOf(0),
				Specs.Product.PRICE
			);

			// when, then
			assertThatExceptionOfType(IllegalArgumentException.class)
				.isThrownBy(() -> menuProductService.changePrice(menu, menuProduct, newPrice))
				.withMessage(ExceptionMessages.Menu.INVALID_PRICE);
		}

		@DisplayName("크다면 수량을 변경할 수 있다.")
		@Test
		void changeQuantity() {
			// given
			Quantity newQuantity = new Quantity(
				Long.MAX_VALUE,
				Specs.MenuProduct.QUANTITY
			);

			// when, then
			assertThatCode(() -> menuProductService.changeQuantity(menu, menuProduct, newQuantity))
				.doesNotThrowAnyException();
		}

		@DisplayName("작다면 수량을 변경할 수 없다.")
		@Test
		void changeQuantityFailByMenuPricePolicy() {
			// given
			Quantity newQuantity = new Quantity(
				0,
				Specs.MenuProduct.QUANTITY
			);

			// when, then
			assertThatExceptionOfType(IllegalArgumentException.class)
				.isThrownBy(() -> menuProductService.changeQuantity(menu, menuProduct, newQuantity))
				.withMessage(ExceptionMessages.Menu.INVALID_PRICE);
		}
	}
}