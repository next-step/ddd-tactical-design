package kitchenpos.menus.tobe.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class MenuTest {

	private UUID dummyMenuGroupId;
	private DisplayedName dummyDisplayedName;

	@BeforeEach
	void setUp() {
		dummyMenuGroupId = UUID.randomUUID();
		dummyDisplayedName = new DisplayedName("이름", new FakeProfanities());
	}

	@DisplayName("메뉴를 생성할 수 있다")
	@ParameterizedTest
	@CsvSource({
			"1000, 1000, 1",
			"20000, 10000, 3",
			"1000, 10000, 2",
	})
	void create(BigDecimal menuPriceParam, BigDecimal productPrice, int menuProductCount) {
		// given
		Price menuPrice = new Price(menuPriceParam);
		MenuProducts menuProducts = new MenuProducts(Collections.singletonList(createMenuProduct(productPrice, menuProductCount)));

		// when
		Menu result = Menu.create(UUID.randomUUID(), dummyMenuGroupId, dummyDisplayedName, menuPrice, menuProducts);

		// then
		assertThat(result).isNotNull();
	}

	@DisplayName("메뉴를 생성 시 메뉴의 가격이 메뉴상품의 총 금액보다 큰 경우 예외가 발생한다")
	@ParameterizedTest
	@CsvSource({
			"1000, 999, 1",
			"20001, 10000, 2",
			"1, 10000, 0",
	})
	void createPrice(BigDecimal menuPriceParam, BigDecimal productPrice, int menuProductCount) {
		// given
		Price menuPrice = new Price(menuPriceParam);
		MenuProducts menuProducts = new MenuProducts(Collections.singletonList(createMenuProduct(productPrice, menuProductCount)));

		// when then
		assertThatThrownBy(() -> Menu.create(UUID.randomUUID(), dummyMenuGroupId, dummyDisplayedName, menuPrice, menuProducts))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessageContaining("메뉴의 가격은 메뉴에 속한 상품 금액의 합보다 작거나 같아야 합니다");
	}

	@DisplayName("메뉴의 가격을 변경할 수 있다")
	@ParameterizedTest
	@CsvSource({
			"1000, 1000, 1",
			"20000, 10000, 3",
			"1000, 10000, 2",
	})
	void changePrice(BigDecimal newMenuPriceParam, BigDecimal productPrice, int menuProductCount) {
		// given
		Price oldMenuPrice = new Price(productPrice.multiply(BigDecimal.valueOf(menuProductCount)));
		Price newMenuPrice = new Price(newMenuPriceParam);

		MenuProducts menuProducts = new MenuProducts(Collections.singletonList(createMenuProduct(productPrice, menuProductCount)));

		Menu menu = Menu.create(UUID.randomUUID(), dummyMenuGroupId, dummyDisplayedName, oldMenuPrice, menuProducts);

		// when then
		assertDoesNotThrow(() -> menu.changePrice(newMenuPrice));
	}

	@DisplayName("메뉴의 가격을 변경 시 변경한 가격이 메뉴상품의 총 금액보다 큰 경우 예외가 발생한다")
	@ParameterizedTest
	@CsvSource({
			"1001, 1000, 1",
			"20001, 10000, 2",
			"1, 10000, 0",
	})
	void changePriceThrow(BigDecimal newMenuPriceParam, BigDecimal productPrice, int menuProductCount) {
		// given
		Price oldMenuPrice = new Price(productPrice.multiply(BigDecimal.valueOf(menuProductCount)));
		Price newMenuPrice = new Price(newMenuPriceParam);

		MenuProducts menuProducts = new MenuProducts(Collections.singletonList(createMenuProduct(productPrice, menuProductCount)));

		Menu menu = Menu.create(UUID.randomUUID(), dummyMenuGroupId, dummyDisplayedName, oldMenuPrice, menuProducts);

		// when then
		assertThatThrownBy(() -> menu.changePrice(newMenuPrice))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessageContaining("메뉴의 가격은 메뉴에 속한 상품 금액의 합보다 작거나 같아야 합니다");
	}

	@DisplayName("메뉴를 전시할 수 있다")
	@Test
	void display() {
		// given
		Price menuPrice = new Price(BigDecimal.valueOf(1_000));
		MenuProducts menuProducts = new MenuProducts(Collections.singletonList(createMenuProduct(BigDecimal.valueOf(1_000), 2)));

		Menu menu = Menu.create(UUID.randomUUID(), dummyMenuGroupId, dummyDisplayedName, menuPrice, menuProducts);

		// when then
		assertDoesNotThrow(menu::display);
	}

	@DisplayName("메뉴를 숨길 수 있다")
	@Test
	void hide() {
		// given
		Price menuPrice = new Price(BigDecimal.valueOf(1_000));
		MenuProducts menuProducts = new MenuProducts(Collections.singletonList(createMenuProduct(BigDecimal.valueOf(1_000), 2)));

		Menu menu = Menu.create(UUID.randomUUID(), dummyMenuGroupId, dummyDisplayedName, menuPrice, menuProducts);

		// when then
		assertDoesNotThrow(menu::hide);
	}

	private MenuProduct createMenuProduct(BigDecimal productPrice, int count) {
		Long seq = 1L;
		Product product = new Product(UUID.randomUUID(), "name", new Price(productPrice));
		MenuProductCount menuProductCount = new MenuProductCount(count);

		return new MenuProduct(seq, product, menuProductCount);
	}
}
