package kitchenpos.menus.tobe.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class MenuPriceTest {

	@ParameterizedTest
	@NullSource
	@ValueSource(strings = {"-1", "-0.01"})
	@DisplayName("메뉴 가격이 null이거나 0 미만이면 메뉴 가격 객체를 생성할 수 없다.")
	void menuPriceOfNullOrNegative(String invalidPrice) {
		// Given
		BigDecimal price = invalidPrice != null ? new BigDecimal(invalidPrice) : null;

		// When & Then
		assertThatExceptionOfType(IllegalArgumentException.class)
			.isThrownBy(() -> MenuPrice.of(price))
			.withMessage(MenuPrice.INVALID_PRICE_ERROR);
	}

	@Test
	@DisplayName("유효한 메뉴 가격을 입력하면 메뉴 가격 객체가 정상적으로 생성된다.")
	void menuPrice() {
		// Given
		BigDecimal validPrice = new BigDecimal("1000");

		// When
		MenuPrice menuPrice = MenuPrice.of(validPrice);

		// Then
		assertEquals(validPrice, menuPrice.getValue());
	}
}
