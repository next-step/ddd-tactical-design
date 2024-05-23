package kitchenpos.product.domain;

import static kitchenpos.product.domain.ProductPrice.*;
import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class ProductPriceTest {

	@ParameterizedTest
	@NullSource
	@ValueSource(strings = {"-1", "-0.01"})
	@DisplayName("상품 가격이 null이거나 0 미만이면 상품 가격 객체를 생성할 수 없다.")
	void productPriceOfNullOrNegative(String invalidPrice) {
		// Given
		BigDecimal price = invalidPrice != null ? new BigDecimal(invalidPrice) : null;

		// When & Then
		assertThatExceptionOfType(IllegalArgumentException.class)
			.isThrownBy(() -> new ProductPrice(price))
			.withMessage(INVALID_PRICE_ERROR);
	}

	@Test
	@DisplayName("유효한 상품 가격을 입력하면 상품 가격 객체가 정상적으로 생성된다.")
	void productPrice() {
		// Given
		BigDecimal validPrice = new BigDecimal("1000");

		// When
		ProductPrice productPrice = new ProductPrice(validPrice);

		// Then
		assertEquals(validPrice, productPrice.getValue());
	}
}
