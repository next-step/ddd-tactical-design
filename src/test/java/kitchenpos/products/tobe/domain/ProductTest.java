package kitchenpos.products.tobe.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import kitchenpos.core.constant.Specs;
import kitchenpos.core.specification.NameSpecification;
import kitchenpos.core.specification.PriceSpecification;
import kitchenpos.products.application.FakePurgomalumClient;

class ProductTest {

	private NameSpecification nameSpecification;
	private PriceSpecification priceSpecification;

	@BeforeEach
	void setUp() {
		nameSpecification = new ProductNameSpecification(new FakePurgomalumClient());
		priceSpecification = Specs.Product.PRICE;
	}

	private Product createDefault() {
		String name = "발베니 더블우드 17년";
		BigDecimal price = BigDecimal.valueOf(120_000L);
		return new Product(name, nameSpecification, price, priceSpecification);
	}

	@DisplayName("상품 생성 시 ")
	@Nested
	class CreateTest {

		@DisplayName("명세를 만족하면 정상적으로 수행된다.")
		@Test
		void create() {
			// given
			Product product = createDefault();

			// then
			assertAll(
				() -> assertThat(product).isNotNull(),
				() -> assertThat(product.getId()).isNotNull(),
				() -> assertThat(product.getName()).isNotBlank(),
				() -> assertThat(product.getPrice()).isNotNull()
			);
		}
	}

	@DisplayName("상품 이름 수정 시 ")
	@Nested
	class ChangeNameTest {

		@DisplayName("명세를 만족하면 정상적으로 수정된다.")
		@Test
		void changeName() {
			// given
			Product product = createDefault();

			String newName = "발베니 더블우드 17년";

			// when
			product.changeName(newName, nameSpecification);

			// then
			assertThat(product.getName()).isEqualTo(newName);
		}

		@DisplayName("명세를 만족하지 않으면 수정에 실패한다.")
		@ParameterizedTest(name = "{displayName} name: {0}")
		@NullAndEmptySource
		void changeNameFailBy(String newName) {
			// given
			Product product = createDefault();

			// then
			assertThatExceptionOfType(IllegalArgumentException.class)
				.isThrownBy(() -> product.changeName(newName, nameSpecification));

			assertThat(product.getName()).isNotEqualTo(newName);
		}
	}

	@DisplayName("상품 가격 수정 시 ")
	@Nested
	class ChangePriceTest {

		@DisplayName("명세를 만족하면 정상적으로 수정된다.")
		@Test
		void changePrice() {
			// given
			Product product = createDefault();

			BigDecimal newPrice = BigDecimal.valueOf(300_000L);

			// when
			product.changePrice(newPrice, priceSpecification);

			// then
			assertThat(product.getPrice()).isEqualTo(newPrice);
		}

		@DisplayName("명세를 만족하지 않으면 수정에 실패한다.")
		@Test
		void changePriceFailByNegativePrice() {
			// given
			Product product = createDefault();

			BigDecimal newPrice = BigDecimal.valueOf(-120_000L);

			// then
			assertThatExceptionOfType(IllegalArgumentException.class)
				.isThrownBy(() -> product.changePrice(newPrice, priceSpecification));

			assertThat(product.getPrice()).isNotEqualTo(newPrice);
		}
	}
}
