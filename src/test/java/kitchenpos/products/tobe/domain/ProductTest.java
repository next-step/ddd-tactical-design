package kitchenpos.products.tobe.domain;

import static kitchenpos.product.domain.ProductPrice.*;
import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import kitchenpos.common.domain.PurgomalumClient;
import kitchenpos.common.infra.FakePurgomalumClient;
import kitchenpos.product.domain.Product;
import kitchenpos.product.domain.ProductName;

class ProductTest {

	private PurgomalumClient purgomalumClient = new FakePurgomalumClient();

	@Test
	@DisplayName("유효한 ID, 이름 및 가격으로 상품 객체를 생성하면 정상적으로 생성된다.")
	void createProductWithValidArguments() {
		// Given
		UUID id = UUID.randomUUID();
		String name = "유효한 상품 이름";
		BigDecimal price = new BigDecimal("1000");

		// When
		Product product = new Product(id, name, price, purgomalumClient);

		// Then
		assertEquals(id, product.getId());
		assertEquals(name, product.getName());
		assertEquals(price, product.getPrice());
	}

	@ParameterizedTest
	@NullAndEmptySource
	@DisplayName("상품 이름이 null이거나 비어있으면 상품 객체를 생성할 수 없다.")
	void createProductWithInvalidName(String invalidName) {
		// Given
		UUID id = UUID.randomUUID();
		BigDecimal price = new BigDecimal("1000");

		// When & Then
		assertThatExceptionOfType(IllegalArgumentException.class)
			.isThrownBy(() -> new Product(id, invalidName, price, purgomalumClient))
			.withMessage(ProductName.NULL_OR_EMPTY_NAME_ERROR);
	}

	@ParameterizedTest
	@ValueSource(strings = {"비속어가 포함된 점심 상품", "욕설이 포함된 조식 상품"})
	@DisplayName("상품 이름에 비속어가 포함되면 상품 객체를 생성할 수 없다.")
	void createProductWithProfanityName() {
		// Given
		UUID id = UUID.randomUUID();
		BigDecimal price = new BigDecimal("1000");
		String nameWithProfanity = "비속어가 포함된 상품 이름";

		// When & Then
		assertThatExceptionOfType(IllegalArgumentException.class)
			.isThrownBy(() -> new Product(id, nameWithProfanity, price, purgomalumClient))
			.withMessage(ProductName.NAME_WITH_PROFANITY_ERROR);
	}

	@ParameterizedTest
	@NullSource
	@ValueSource(strings = {"-1000", "-0.0394"})
	@DisplayName("상품 가격이 null이거나 0 미만이면 상품 객체를 생성할 수 없다.")
	void createProductWithInvalidPrice(BigDecimal invalidPrice) {
		// Given
		UUID id = UUID.randomUUID();
		String name = "유효한 상품 이름";

		// When & Then
		assertThatExceptionOfType(IllegalArgumentException.class)
			.isThrownBy(() -> new Product(id, name, invalidPrice, purgomalumClient))
			.withMessage(INVALID_PRICE_ERROR);
	}

	@Test
	@DisplayName("유효한 새로운 가격으로 가격을 변경하면 상품 가격이 정상적으로 변경된다.")
	void changePriceWithValidPrice() {
		// Given
		UUID id = UUID.randomUUID();
		String name = "유효한 상품 이름";
		BigDecimal initialPrice = new BigDecimal("1000");
		BigDecimal newPrice = new BigDecimal("2000");

		Product product = new Product(id, name, initialPrice, purgomalumClient);

		// When
		product.changePrice(newPrice);

		// Then
		assertEquals(newPrice, product.getPrice());
	}

	@ParameterizedTest
	@NullSource
	@ValueSource(strings = {"-1000", "-0.0394"})
	@DisplayName("새로운 상품 가격이 null이거나 0 미만이면 상품 가격을 변경할 수 없다.")
	void changePriceWithInvalidPrice(BigDecimal invalidNewPrice) {
		// Given
		UUID id = UUID.randomUUID();
		String name = "유효한 상품 이름";
		BigDecimal initialPrice = new BigDecimal("1000");
		Product product = new Product(id, name, initialPrice, purgomalumClient);

		// When & Then
		assertThatExceptionOfType(IllegalArgumentException.class)
			.isThrownBy(() -> product.changePrice(invalidNewPrice))
			.withMessage(INVALID_PRICE_ERROR);
	}
}