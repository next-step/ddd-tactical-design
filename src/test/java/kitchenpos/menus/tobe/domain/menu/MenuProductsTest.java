package kitchenpos.menus.tobe.domain.menu;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import kitchenpos.common.domain.Price;
import kitchenpos.menus.tobe.domain.menu.fixture.MenuProductFixture;
import kitchenpos.menus.tobe.domain.menu.fixture.MenuProductsFixture;
import kitchenpos.products.tobe.domain.fixture.ProductFixture;

public class MenuProductsTest {

	@Test
	void 메뉴상품들을_생성할_수_있다() {
		// given
		MenuProduct menuProduct1 = MenuProductFixture.메뉴상품(ProductFixture.상품("강정치킨", 17_000L), 1);
		MenuProduct menuProduct2 = MenuProductFixture.메뉴상품(ProductFixture.상품("양념치킨", 18_000L), 2);

		// when
		new MenuProducts(Arrays.asList(menuProduct1, menuProduct2));
	}

	@Test
	void 메뉴상품들은_한개_이상이어야만_생성할_수_있다() {
		// given
		List<MenuProduct> empty = Collections.emptyList();

		// when & then
		assertThatThrownBy(
			() -> new MenuProducts(empty)
		).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void 메뉴상품들의_전체_금액을_구할_수_있다() {
		// given
		MenuProducts menuProducts = MenuProductsFixture.메뉴상품들(
			MenuProductFixture.메뉴상품(ProductFixture.상품("강정치킨", 17_000L), 1),
			MenuProductFixture.메뉴상품(ProductFixture.상품("양념치킨", 18_000L), 2)
		);

		// when
		Price actual = menuProducts.getTotalPrice();

		// then
		assertThat(actual).isEqualTo(new Price(new BigDecimal(53_000L)));
	}
}
