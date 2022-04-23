package kitchenpos.menus.tobe.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.util.UUID;

class MenuProductTest {


	@DisplayName("메뉴상품을 생성할 수 있다")
	@Test
	void create() {
		// given
		Long seq = 100L;
		Product product = new Product(UUID.randomUUID(), "후라이드", new Price(BigDecimal.valueOf(16000)));
		MenuProductCount count = new MenuProductCount(0);

		// when
		MenuProduct result = new MenuProduct(seq, product, count);

		// then
		Assertions.assertThat(result).isNotNull();
	}

	@DisplayName("메뉴상품의 총 가격을 계산한다")
	@CsvSource({
			"16000, 2, 32000",
			"1000, 0, 0"
	})
	@ParameterizedTest
	void calculatePrice(BigDecimal productPrice, int count, BigDecimal exptectedParam) {
		// given
		MenuProduct menuProduct = createMenuProduct(productPrice, count);
		Price expected = new Price(exptectedParam);

		// when
		Price result = menuProduct.calculatePrice();

		// then
		Assertions.assertThat(result).isEqualTo(expected);
	}

	@DisplayName("seq가 같으면 동등한 메뉴상품이다")
	@Test
	void equality() {
		// given
		Long seq = 100L;

		// when
		MenuProduct menuProduct1 = new MenuProduct(seq, new Product(UUID.randomUUID(), "후라이드", new Price(BigDecimal.valueOf(16000))), new MenuProductCount(1));
		MenuProduct menuProduct2 = new MenuProduct(seq, new Product(UUID.randomUUID(), "양념1+1", new Price(BigDecimal.valueOf(30000))), new MenuProductCount(2));

		
		// then
		Assertions.assertThat(menuProduct1).isEqualTo(menuProduct2);
	}

	private MenuProduct createMenuProduct(BigDecimal productPrice, int countParam) {
		Long seq = 100L;
		MenuProductCount count = new MenuProductCount(countParam);

		Product product = new Product(UUID.randomUUID(), "후라이드", new Price(productPrice));

		return new MenuProduct(seq, product, count);
	}
}
