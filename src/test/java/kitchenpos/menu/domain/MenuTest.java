package kitchenpos.menu.domain;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import kitchenpos.common.domain.PurgomalumClient;
import kitchenpos.common.infra.FakePurgomalumClient;
import kitchenpos.menu.application.dto.MenuCreationRequest;
import kitchenpos.product.domain.Product;

class MenuTest {

	private MenuGroup menuGroup;
	private Product product;
	private PurgomalumClient purgomalumClient = new FakePurgomalumClient();

	@BeforeEach
	void setUp() {
		menuGroup = new MenuGroup("메뉴 그룹");
		product = new Product("후라이드", BigDecimal.valueOf(16000), purgomalumClient);
	}

	@Nested
	class Create {

		@ParameterizedTest
		@NullSource
		@ValueSource(strings = {"-1"})
		@DisplayName("메뉴 생성 시 가격이 null이거나 0미만이면 메뉴를 생성할 수 없다")
		void createMenuWithInvalidPrice(BigDecimal price) {
			// given
			MenuCreationRequest request = new MenuCreationRequest(
				UUID.randomUUID(),
				"후라이드+후라이드",
				price,
				true,
				Map.of(UUID.randomUUID(), 2L)
			);

			// then
			assertThatExceptionOfType(IllegalArgumentException.class)
				.isThrownBy(() -> {
					// when
					new Menu(
						request.name(),
						request.price(),
						menuGroup,
						List.of(new MenuProduct(product, 2L)),
						request.displayed(),
						purgomalumClient
					);
				});
		}

		@ParameterizedTest
		@NullAndEmptySource
		@DisplayName("메뉴 생성 시 메뉴 상품 목록이 null이거나 비어있으면 메뉴를 생성할 수 없다")
		void createMenuWithNoMenuProducts(List<MenuProduct> menuProducts) {
			// given
			MenuPrice menuPrice = MenuPrice.of(BigDecimal.valueOf(19000));

			// then
			assertThatExceptionOfType(IllegalArgumentException.class)
				.isThrownBy(() -> {
					// when
					new Menu(
						"후라이드+후라이드",
						menuPrice.getValue(),
						menuGroup,
						menuProducts,
						true,
						purgomalumClient
					);
				});
		}

		@Test
		@DisplayName("메뉴 생성 시 상품의 수량이 음수인 경우 메뉴를 생성할 수 없다")
		void createMenuWithNegativeQuantity() {
			// then
			assertThatExceptionOfType(IllegalArgumentException.class)
				.isThrownBy(() -> {
					// given & when
					new Menu(
						"후라이드+후라이드",
						BigDecimal.valueOf(19000),
						menuGroup,
						List.of(new MenuProduct(product, -1L)),
						true,
						purgomalumClient
					);
				});
		}

		@ParameterizedTest
		@NullSource
		@ValueSource(strings = {"비속어"})
		@DisplayName("메뉴 생성 시 메뉴 이름이 null이거나 비속어가 포함되어 있으면 메뉴를 생성할 수 없다")
		void createMenuWithInvalidProductNames(String name) {
			// given
			MenuProduct validProduct = new MenuProduct(product, 2L);

			// then
			assertThatExceptionOfType(IllegalArgumentException.class)
				.isThrownBy(() -> {
					// when
					new Menu(
						name,
						BigDecimal.valueOf(19000),
						menuGroup,
						List.of(validProduct),
						true,
						purgomalumClient
					);
				});
		}

		@ParameterizedTest
		@ValueSource(strings = {"6.99", "8.99"})
		@DisplayName("메뉴를 정상적으로 생성할 수 있다")
		void createMenuWithValidProductPricesAndNames(BigDecimal price) {
			// given
			MenuProduct validProduct = new MenuProduct(product, 2L);

			// when
			Menu menu = new Menu(
				"후라이드+후라이드",
				price,
				menuGroup,
				List.of(validProduct),
				true,
				purgomalumClient
			);

			// then
			assertThat(menu).isNotNull();
		}
	}

	@Nested
	class ChangePrice {

		@ParameterizedTest
		@NullSource
		@ValueSource(strings = {"-1", "-100.0"})
		@DisplayName("메뉴 가격 변경 시 가격이 null이거나 0미만인 경우 메뉴 가격을 변경할 수 없다")
		void changePriceWithInvalidPrice(BigDecimal price) {
			// given
			Menu menu = new Menu(
				"후라이드+후라이드",
				BigDecimal.valueOf(19000),
				menuGroup,
				List.of(new MenuProduct(product, 2L)),
				true,
				purgomalumClient
			);

			// then
			assertThatExceptionOfType(IllegalArgumentException.class)
				.isThrownBy(() -> {
					// when
					menu.changePrice(price);
				});
		}

		@ParameterizedTest
		@ValueSource(strings = {"32001", "32000.001"})
		@DisplayName("메뉴 가격 변경 시 변경 가격이 상품 가격 총합보다 클 때 메뉴 가격을 변경할 수 없다")
		void changePriceWithExcessivePrice(BigDecimal price) {
			// given
			Menu menu = new Menu(
				"후라이드+후라이드",
				BigDecimal.valueOf(32000),
				menuGroup,
				List.of(new MenuProduct(product, 2L)),
				true,
				purgomalumClient
			);

			// then
			assertThatExceptionOfType(IllegalArgumentException.class)
				.isThrownBy(() -> {
					// when
					menu.changePrice(price);
				});
		}

		@ParameterizedTest
		@ValueSource(strings = {"15.00", "17.98"})
		@DisplayName("메뉴 가격 변경 시 변경 가격이 상품 가격 총합보다 작거나 같을 때 메뉴 가격을 변경할 수 있다")
		void changePriceWithValidPrice(BigDecimal price) {
			// given
			Menu menu = new Menu(
				"후라이드+후라이드",
				BigDecimal.valueOf(19000),
				menuGroup,
				List.of(new MenuProduct(product, 2L)),
				true,
				purgomalumClient
			);

			// when
			menu.changePrice(price);

			// then
			assertThat(menu.getPrice()).isEqualTo(price);
		}
	}

	@Nested
	class Display {

		@ParameterizedTest
		@ValueSource(strings = {"32000", "31999", "31999.9999"})
		@DisplayName("메뉴 노출 시 메뉴 가격이 상품 가격 총합과 같거나 작으면 메뉴를 노출할 수 있다")
		void displayMenuSuccessfully(BigDecimal price) {
			// given
			Menu menu = new Menu(
				"후라이드+후라이드",
				price,
				menuGroup,
				List.of(new MenuProduct(product, 2L)),
				false,
				purgomalumClient
			);

			// when
			menu.display(true);

			// then
			assertThat(menu.isDisplayed()).isTrue();
		}
	}

	@Nested
	class Hide {

		@Test
		@DisplayName("메뉴 숨김 처리를 할 수 있다")
		void hideMenuSuccessfully() {
			// given
			Menu menu = new Menu(
				"후라이드+후라이드",
				BigDecimal.valueOf(19000),
				menuGroup,
				List.of(new MenuProduct(product, 2L)),
				true,
				purgomalumClient
			);

			// when
			menu.display(false);

			// then
			assertThat(menu.isDisplayed()).isFalse();
		}
	}
}