package kitchenpos.menus.tobe.application;

import static kitchenpos.TobeFixtures.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.util.CollectionUtils;

import kitchenpos.common.tobe.domain.PurgomalumClient;
import kitchenpos.common.tobe.infra.FakePurgomalumClient;
import kitchenpos.menus.tobe.application.dto.MenuCreationRequest;
import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuGroupRepository;
import kitchenpos.menus.tobe.domain.MenuProduct;
import kitchenpos.menus.tobe.domain.MenuRepository;
import kitchenpos.menus.tobe.infra.InMemoryMenuGroupRepository;
import kitchenpos.menus.tobe.infra.InMemoryMenuRepository;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import kitchenpos.products.tobe.infra.InMemoryProductRepository;

class DefaultMenuServiceTest {
	private MenuRepository menuRepository;
	private MenuGroupRepository menuGroupRepository;
	private ProductRepository productRepository;
	private PurgomalumClient purgomalumClient;
	private MenuService menuService;
	private UUID menuGroupId;
	private Product product;

	@BeforeEach
	void setUp() {
		menuRepository = new InMemoryMenuRepository();
		menuGroupRepository = new InMemoryMenuGroupRepository();
		productRepository = new InMemoryProductRepository();
		purgomalumClient = new FakePurgomalumClient();
		menuService = new DefaultMenuService(menuRepository, menuGroupRepository, productRepository, purgomalumClient);
		menuGroupId = menuGroupRepository.save(menuGroup()).getId();
		product = productRepository.save(product("후라이드", 16_000L));
	}

	@DisplayName("1개 이상의 등록된 상품으로 메뉴를 등록할 수 있다.")
	@Test
	void create() {
		final MenuCreationRequest expected = createMenuRequest(
			"후라이드+후라이드",
			19_000L,
			menuGroupId,
			true,
			createMenuProductQuantities(product, 2L)
		);
		final Menu actual = menuService.create(expected);
		assertThat(actual).isNotNull();
		assertAll(
			() -> assertThat(actual.getId()).isNotNull(),
			() -> assertThat(actual.getName()).isEqualTo(expected.name()),
			() -> assertThat(actual.getPrice()).isEqualTo(expected.price()),
			() -> assertThat(actual.getMenuGroup().getId()).isEqualTo(expected.menuGroupId()),
			() -> assertThat(actual.isDisplayed()).isEqualTo(expected.displayed()),
			() -> assertThat(actual.getMenuProducts()).hasSize(1)
		);
	}

	@DisplayName("상품이 없으면 등록할 수 없다.")
	@NullAndEmptySource
	@ParameterizedTest
	void create(final List<MenuProduct> menuProducts) {
		final MenuCreationRequest expected = createMenuRequest(
			"후라이드+후라이드",
			19_000L,
			menuGroupId,
			true,
			createMenuProductQuantities(menuProducts)
		);
		assertThatThrownBy(() -> menuService.create(expected))
			.isInstanceOf(IllegalArgumentException.class);
	}

	@DisplayName("메뉴에 속한 상품의 수량은 0개 이상이어야 한다.")
	@Test
	void createNegativeQuantity() {
		final MenuCreationRequest expected = createMenuRequest(
			"후라이드+후라이드",
			19_000L,
			menuGroupId,
			true,
			createMenuProductQuantities(product.getId(), -1L)
		);
		assertThatThrownBy(() -> menuService.create(expected))
			.isInstanceOf(IllegalArgumentException.class);
	}

	@DisplayName("메뉴의 가격이 올바르지 않으면 등록할 수 없다.")
	@ValueSource(strings = "-1000")
	@NullSource
	@ParameterizedTest
	void create(final BigDecimal price) {
		final MenuCreationRequest expected = createMenuRequest(
			"후라이드+후라이드",
			price,
			menuGroupId,
			true,
			createMenuProductQuantities(product.getId(), 2L)
		);
		assertThatThrownBy(() -> menuService.create(expected))
			.isInstanceOf(IllegalArgumentException.class);
	}

	@DisplayName("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.")
	@Test
	void createExpensiveMenu() {
		final MenuCreationRequest expected = createMenuRequest(
			"후라이드+후라이드", 33_000L, menuGroupId, true, createMenuProductQuantities(product.getId(), 2L)
		);
		assertThatThrownBy(() -> menuService.create(expected))
			.isInstanceOf(IllegalArgumentException.class);
	}

	@DisplayName("메뉴는 특정 메뉴 그룹에 속해야 한다.")
	@NullSource
	@ParameterizedTest
	void create(final UUID menuGroupId) {
		final MenuCreationRequest expected = createMenuRequest(
			"후라이드+후라이드",
			19_000L,
			menuGroupId,
			true,
			createMenuProductQuantities(product.getId(), 2L)
		);
		assertThatThrownBy(() -> menuService.create(expected))
			.isInstanceOf(NoSuchElementException.class);
	}

	@DisplayName("메뉴의 이름이 올바르지 않으면 등록할 수 없다.")
	@ValueSource(strings = {"비속어", "욕설이 포함된 이름"})
	@NullSource
	@ParameterizedTest
	void create(final String name) {
		final MenuCreationRequest expected = createMenuRequest(
			name,
			19_000L,
			menuGroupId,
			true,
			createMenuProductQuantities(product.getId(), 2L)
		);
		assertThatThrownBy(() -> menuService.create(expected))
			.isInstanceOf(IllegalArgumentException.class);
	}

	@DisplayName("메뉴의 가격을 변경할 수 있다.")
	@Test
	void changePrice() {
		final UUID menuId = menuRepository.save(menu(19_000L, menuProduct(product, 2L))).getId();
		final BigDecimal expected = BigDecimal.valueOf(16_000L);
		final Menu actual = menuService.changePrice(menuId, expected);
		assertThat(actual.getPrice()).isEqualTo(expected);
	}

	@DisplayName("메뉴의 가격이 올바르지 않으면 변경할 수 없다.")
	@ValueSource(strings = "-1000")
	@NullSource
	@ParameterizedTest
	void changePrice(final BigDecimal requestPrice) {
		final UUID menuId = menuRepository.save(menu(19_000L, menuProduct(product, 2L))).getId();
		assertThatThrownBy(() -> menuService.changePrice(menuId, requestPrice))
			.isInstanceOf(IllegalArgumentException.class);
	}

	@DisplayName("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.")
	@Test
	void changePriceToExpensive() {
		final UUID menuId = menuRepository.save(menu(19_000L, menuProduct(product, 2L))).getId();
		final BigDecimal expected = BigDecimal.valueOf(33_000L);
		assertThatThrownBy(() -> menuService.changePrice(menuId, expected))
			.isInstanceOf(IllegalArgumentException.class);
	}

	@DisplayName("메뉴를 노출할 수 있다.")
	@Test
	void display() {
		final UUID menuId = menuRepository.save(menu(19_000L, false, menuProduct(product, 2L))).getId();
		final Menu actual = menuService.display(menuId);
		assertThat(actual.isDisplayed()).isTrue();
	}

	@DisplayName("메뉴를 숨길 수 있다.")
	@Test
	void hide() {
		final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct(product, 2L))).getId();
		final Menu actual = menuService.hide(menuId);
		assertThat(actual.isDisplayed()).isFalse();
	}

	@DisplayName("메뉴의 목록을 조회할 수 있다.")
	@Test
	void findAll() {
		menuRepository.save(menu(19_000L, true, menuProduct(product, 2L)));
		final List<Menu> actual = menuService.findAll();
		assertThat(actual).hasSize(1);
	}

	private MenuCreationRequest createMenuRequest(
		final String name,
		final long price,
		final UUID menuGroupId,
		final boolean displayed,
		final Map<UUID, Long> menuProductQuantities
	) {
		return createMenuRequest(
			name,
			BigDecimal.valueOf(price),
			menuGroupId,
			displayed,
			menuProductQuantities
		);
	}

	private MenuCreationRequest createMenuRequest(
		final String name,
		final BigDecimal price,
		final UUID menuGroupId,
		final boolean displayed,
		final Map<UUID, Long> menuProductQuantities
	) {
		return new MenuCreationRequest(
			menuGroupId,
			name,
			price,
			displayed,
			menuProductQuantities
		);
	}

	private static Map<UUID, Long> createMenuProductQuantities(final Product product, final long quantity) {
		return createMenuProductQuantities(product.getId(), quantity);
	}

	private static Map<UUID, Long> createMenuProductQuantities(final UUID productId, final long quantity) {
		return Map.of(productId, quantity);
	}

	private static Map<UUID, Long> createMenuProductQuantities(final List<MenuProduct> menuProducts) {
		if (CollectionUtils.isEmpty(menuProducts)) {
			return Collections.emptyMap();
		}

		return menuProducts
			.stream()
			.collect(Collectors.toUnmodifiableMap(MenuProduct::getProductId, MenuProduct::getQuantity));
	}
}
