package kitchenpos.menus.tobe.application.menu;

import static kitchenpos.Fixtures.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import kitchenpos.common.domain.Price;
import kitchenpos.common.domain.Profanities;
import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.menus.tobe.domain.menu.MenuRepository;
import kitchenpos.menus.tobe.domain.menu.fixture.MenuFixture;
import kitchenpos.menus.tobe.domain.menugroup.MenuGroupRepository;
import kitchenpos.menus.tobe.domain.menugroup.fixture.MenuGroupFixture;
import kitchenpos.menus.tobe.infra.menu.InMemoryMenuRepository;
import kitchenpos.menus.tobe.infra.menugroup.InMemoryMenuGroupRepository;
import kitchenpos.menus.tobe.ui.menu.MenuCreateRequest;
import kitchenpos.menus.tobe.ui.menu.MenuPriceChangeRequest;
import kitchenpos.menus.tobe.ui.menu.MenuProductDto;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import kitchenpos.products.tobe.domain.fixture.ProductFixture;
import kitchenpos.products.tobe.infra.InMemoryProductRepository;

public class MenuServiceTest {
	private MenuRepository menuRepository;
	private MenuGroupRepository menuGroupRepository;
	private ProductRepository productRepository;
	private Profanities profanities;
	private MenuService menuService;
	private UUID menuGroupId;
	private Product product;

	@BeforeEach
	void setUp() {
		menuRepository = new InMemoryMenuRepository();
		menuGroupRepository = new InMemoryMenuGroupRepository();
		productRepository = new InMemoryProductRepository();
		profanities = text -> Stream.of("비속어", "욕설").anyMatch(text::contains);
		menuService = new MenuService(menuGroupRepository, productRepository, profanities, menuRepository);
		menuGroupId = menuGroupRepository.save(MenuGroupFixture.메뉴그룹("추천메뉴")).getId();
		product = productRepository.save(ProductFixture.상품("후라이드", 16_000L));
	}

	@Test
	void 한개_이상의_등록된_상품으로_메뉴를_등록할_수_있다() {
		// given
		MenuCreateRequest request = new MenuCreateRequest(
			"후라이드+후라이드",
			19_000L,
			menuGroupId,
			true,
			Collections.singletonList(new MenuProductDto(product.getId(), 2L))
		);

		// when
		Menu actual = menuService.create(request);

		// then
		assertThat(actual).isNotNull();
		assertAll(
			() -> assertThat(actual.getId()).isNotNull(),
			() -> assertThat(actual.getName().getValue()).isEqualTo(request.getName()),
			() -> assertThat(actual.getPrice().getValue().longValue()).isEqualTo(request.getPrice()),
			() -> assertThat(actual.getMenuGroup().getId()).isEqualTo(request.getMenuGroupId()),
			() -> assertThat(actual.isDisplayed()).isEqualTo(request.getDisplayed().booleanValue()),
			() -> assertThat(actual.getMenuProducts()).isNotNull(),
			() -> assertThat(actual.getMenuProducts().getValues()).hasSize(1)
		);
	}

	@ParameterizedTest
	@MethodSource("menuProducts")
	void 상품이_없으면_등록할_수_없다(List<MenuProductDto> menuProducts) {
		// given
		MenuCreateRequest request = new MenuCreateRequest(
			"후라이드+후라이드",
			19_000L,
			menuGroupId,
			true,
			menuProducts
		);

		// when & then
		assertThatThrownBy(() -> menuService.create(request))
			.isInstanceOf(IllegalArgumentException.class);
	}

	private static List<Arguments> menuProducts() {
		return Arrays.asList(
			Arguments.of(Collections.emptyList()),
			Arguments.of(Collections.singletonList(new MenuProductDto(INVALID_ID, 2L)))
		);
	}

	@Test
	void 메뉴에_속한_상품의_수량은_0개_이상이어야_한다() {
		// given
		MenuCreateRequest request = new MenuCreateRequest(
			"후라이드+후라이드",
			19_000L,
			menuGroupId,
			true,
			Collections.singletonList(new MenuProductDto(product.getId(), -1L))
		);

		// when & then
		assertThatThrownBy(() -> menuService.create(request))
			.isInstanceOf(IllegalArgumentException.class);
	}

	@ParameterizedTest
	@ValueSource(strings = "-1000")
	void 메뉴의_가격이_올바르지_않으면_등록할_수_없다(long price) {
		// given
		MenuCreateRequest request = new MenuCreateRequest(
			"후라이드+후라이드",
			price,
			menuGroupId,
			true,
			Collections.singletonList(new MenuProductDto(product.getId(), 2L))
		);

		// when & then
		assertThatThrownBy(() -> menuService.create(request))
			.isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void 메뉴에_속한_상품_금액의_합은_메뉴의_가격보다_크거나_같아야_한다() {
		// given
		MenuCreateRequest request = new MenuCreateRequest(
			"후라이드+후라이드",
			70_000L,
			menuGroupId,
			true,
			Collections.singletonList(new MenuProductDto(product.getId(), 2L))
		);

		// when & then
		assertThatThrownBy(() -> menuService.create(request))
			.isInstanceOf(IllegalArgumentException.class);
	}

	@ParameterizedTest
	@NullSource
	void 메뉴는_특정_메뉴_그룹에_속해야_한다(UUID menuGroupId) {
		// given
		MenuCreateRequest request = new MenuCreateRequest(
			"후라이드+후라이드",
			19_000L,
			menuGroupId,
			true,
			Collections.singletonList(new MenuProductDto(product.getId(), 2L))
		);

		// when & then
		assertThatThrownBy(() -> menuService.create(request))
			.isInstanceOf(NoSuchElementException.class);
	}

	@ParameterizedTest
	@ValueSource(strings = {"비속어", "욕설이 포함된 이름"})
	@NullSource
	void 메뉴의_이름이_올바르지_않으면_등록할_수_없다(String name) {
		// given
		MenuCreateRequest request = new MenuCreateRequest(
			name,
			19_000L,
			menuGroupId,
			true,
			Collections.singletonList(new MenuProductDto(product.getId(), 2L))
		);

		// when & then
		assertThatThrownBy(() -> menuService.create(request))
			.isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void 메뉴의_가격을_변경할_수_있다() {
		// given
		UUID menuId = menuRepository.save(MenuFixture.전시_메뉴()).getId();
		MenuPriceChangeRequest request = new MenuPriceChangeRequest(16_000L);

		// when
		Menu actual = menuService.changePrice(menuId, request);

		// then
		assertThat(actual.getPrice()).isEqualTo(new Price(new BigDecimal(request.getPrice())));
	}

	@ParameterizedTest
	@ValueSource(strings = "-1000")
	void 메뉴의_가격이_올바르지_않으면_변경할_수_없다(long price) {
		// given
		UUID menuId = menuRepository.save(MenuFixture.전시_메뉴()).getId();
		MenuPriceChangeRequest request = new MenuPriceChangeRequest(price);

		// when & then
		assertThatThrownBy(() -> menuService.changePrice(menuId, request))
			.isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void 메뉴의_가격이_메뉴에_속한_상품_금액의_합보다_높을_경우_메뉴를_노출할_수_없다() {
		// given
		UUID menuId = menuRepository.save(MenuFixture.전시_메뉴(new Price(new BigDecimal(19_000L)))).getId();
		MenuPriceChangeRequest request = new MenuPriceChangeRequest(70_000L);

		// when & then
		assertThatThrownBy(() -> menuService.changePrice(menuId, request))
			.isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void 메뉴를_노출할_수_있다() {
		// given
		UUID menuId = menuRepository.save(MenuFixture.숨김_메뉴()).getId();

		// when
		Menu actual = menuService.display(menuId);

		// then
		assertThat(actual.isDisplayed()).isTrue();
	}

	@Test
	void 메뉴를_숨길_수_있다() {
		// given
		UUID menuId = menuRepository.save(MenuFixture.전시_메뉴()).getId();

		// when
		Menu actual = menuService.hide(menuId);

		// then
		assertThat(actual.isDisplayed()).isFalse();
	}

	@Test
	void 메뉴의_목록을_조회할_수_있다() {
		// given
		menuRepository.save(MenuFixture.전시_메뉴());

		// when
		List<Menu> actual = menuService.findAll();

		// then
		assertThat(actual).hasSize(1);
	}
}
