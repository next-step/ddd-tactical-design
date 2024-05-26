package kitchenpos.products.tobe.domain;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import kitchenpos.common.domain.PurgomalumClient;
import kitchenpos.common.infra.FakePurgomalumClient;
import kitchenpos.product.domain.ProductName;

class ProductNameTest {
	private PurgomalumClient purgomalumClient = new FakePurgomalumClient();


	@ParameterizedTest
	@NullAndEmptySource
	@DisplayName("상품 이름이 null이거나 비어있으면 상품 이름 객체를 생성할 수 없다.")
	void productNameOfNullOrEmpty(String invalidName) {
		// When & Then
		assertThatExceptionOfType(IllegalArgumentException.class)
			.isThrownBy(() -> new ProductName(invalidName, purgomalumClient))
			.withMessage(ProductName.NULL_OR_EMPTY_NAME_ERROR);
	}

	@ParameterizedTest
	@ValueSource(strings = {"비속어가 포함된 상품 이름", "욕설이 포함된 상품 이름"})
	@DisplayName("상품 이름에 비속어가 포함되면 상품 이름 객체를 생성할 수 없다.")
	void productNameWithProfanity(String nameWithProfanity) {
		// When & Then
		assertThatExceptionOfType(IllegalArgumentException.class)
			.isThrownBy(() -> new ProductName(nameWithProfanity, purgomalumClient))
			.withMessage(ProductName.NAME_WITH_PROFANITY_ERROR);
	}

	@Test
	@DisplayName("유효한 상품 이름을 입력하면 상품 이름 객체가 정상적으로 생성된다.")
	void productName() {
		// Given
		String validName = "유효한 상품 이름";

		// When
		ProductName productName = new ProductName(validName, purgomalumClient);

		// Then
		assertEquals(validName, productName.getValue());
	}
}