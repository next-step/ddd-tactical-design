package kitchenpos.menus.tobe.domain.menu;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.fixture.ProductFixture;

class MenuProductTest {
	@Test
	void 메뉴상품을_생성할_수_있다() {
		// given
		Product product = ProductFixture.상품("강정치킨", 17_000L);
		Quantity quantity = new Quantity(25L);

		// when
		MenuProduct menuProduct = new MenuProduct(product, quantity);

		// & then
		assertAll(
			() -> assertThat(menuProduct).isNotNull(),
			() -> assertThat(menuProduct.getProduct()).isEqualTo(product),
			() -> assertThat(menuProduct.getQuantity()).isEqualTo(quantity)
		);
	}
}