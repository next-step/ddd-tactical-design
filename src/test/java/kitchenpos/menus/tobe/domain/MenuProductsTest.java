package kitchenpos.menus.tobe.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MenuProductsTest {

	@DisplayName("메뉴상품들을 생성할 수 있다")
	@Test
	void create() {
		// given
		MenuProduct menuProduct1 = createMenuProduct(1L, 10_000, 2);
		MenuProduct menuProduct2 = createMenuProduct(2L, 6_000, 1);

		List<MenuProduct> menuProducts = Arrays.asList(menuProduct1, menuProduct2);

		// when
		MenuProducts result = new MenuProducts(menuProducts);

		// then
		Assertions.assertThat(result).isNotNull();
	}

	@DisplayName("메뉴상품들에는 메뉴상품이 존재하지 않는 경우 예외가 발생한다")
	@NullAndEmptySource
	@ParameterizedTest
	void empty(List<MenuProduct> menuProducts) {
		// when then
		assertThatThrownBy(() -> new MenuProducts(menuProducts))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessageContaining("MenuProduct가 1개 이상 존재해야 합니다");
	}

	@DisplayName("메뉴상품들에 동등한 메뉴상품이 존재하는 경우 예외가 발생한다")
	@Test
	void equalMenuProduct() {
		// given
		MenuProduct menuProduct1 = createMenuProduct(1L, 10_000, 2);
		MenuProduct menuProduct2 = createMenuProduct(1L, 6_000, 1);

		List<MenuProduct> menuProducts = Arrays.asList(menuProduct1, menuProduct2);

		// when then
		assertThatThrownBy(() -> new MenuProducts(menuProducts))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessageContaining("동등한 MenuProduct가 존재할 수 없습니다");
	}

	@DisplayName("메뉴상품들의 총 가격을 계산한다")
	@Test
	void calculatePrice() {
		// given
		MenuProduct menuProduct1 = createMenuProduct(1L, 10_000, 2);
		MenuProduct menuProduct2 = createMenuProduct(2L, 6_000, 1);

		MenuProducts menuProducts = new MenuProducts(Arrays.asList(menuProduct1, menuProduct2));

		Price expected = new Price(BigDecimal.valueOf(26_000));

		// when
		Price result = menuProducts.calculatePrice();

		// then
		Assertions.assertThat(result).isEqualTo(expected);
	}

	private MenuProduct createMenuProduct(Long seq, int productPrice, int count) {
		return new MenuProduct(seq, new Product(UUID.randomUUID(), "후라이드", new Price(BigDecimal.valueOf(productPrice))), new MenuProductCount(count));
	}
}
