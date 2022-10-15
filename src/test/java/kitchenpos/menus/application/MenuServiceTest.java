package kitchenpos.menus.application;

import kitchenpos.menus.model.MenuModel;
import kitchenpos.menus.model.MenuProductRequest;
import kitchenpos.menus.model.MenuRequest;
import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuGroupRepository;
import kitchenpos.menus.domain.MenuProduct;
import kitchenpos.menus.tobe.domain.MenuRepository;
import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.application.InMemoryProductRepository;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.*;

import static kitchenpos.Fixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class MenuServiceTest {
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
        menuService = new MenuService(menuRepository, menuGroupRepository, productRepository, purgomalumClient);
        menuGroupId = menuGroupRepository.save(menuGroup()).id();
        product = productRepository.save(product("후라이드", 16_000L));
    }

    @DisplayName("1개 이상의 등록된 상품으로 메뉴를 등록할 수 있다.")
    @Test
    void create() {
        final MenuRequest expected = createMenuRequest(
            "후라이드+후라이드", 19_000L, menuGroupId, true, createMenuProductRequest(product.id(), 2L)
        );
        final MenuModel actual = menuService.create(expected.price(), expected.menuGroupId(), expected.name(),
                expected.isDisplayed(), expected.menuProductRequests());
        assertThat(actual).isNotNull();
        assertAll(
            () -> assertThat(actual.id()).isNotNull(),
            () -> assertThat(actual.name()).isEqualTo(expected.name()),
            () -> assertThat(actual.price()).isEqualTo(expected.price()),
            () -> assertThat(actual.menuGroup().id()).isEqualTo(expected.menuGroupId()),
            () -> assertThat(actual.isDisplayed()).isEqualTo(expected.isDisplayed()),
            () -> assertThat(actual.menuProducts()).hasSize(1)
        );
    }

    @DisplayName("상품이 없으면 등록할 수 없다.")
    @MethodSource("menuProducts")
    @ParameterizedTest
    void create(final List<MenuProductRequest> menuProducts) {
        final MenuRequest expected = createMenuRequest("후라이드+후라이드", 19_000L, menuGroupId, true, menuProducts);
        assertThatThrownBy(() -> menuService.create(expected.price(), expected.menuGroupId(), expected.name(),
                expected.isDisplayed(), expected.menuProductRequests()))
            .isInstanceOf(IllegalArgumentException.class);
    }

    private static List<Arguments> menuProducts() {
        return Arrays.asList(
            null,
            Arguments.of(Collections.emptyList()),
            Arguments.of(Arrays.asList(createMenuProductRequest(INVALID_ID, 2L)))
        );
    }

    @DisplayName("메뉴에 속한 상품의 수량은 0개 이상이어야 한다.")
    @Test
    void createNegativeQuantity() {
        final MenuRequest expected = createMenuRequest(
            "후라이드+후라이드", 19_000L, menuGroupId, true, createMenuProductRequest(product.id(), -1L)
        );
        assertThatThrownBy(() -> menuService.create(expected.price(), expected.menuGroupId(), expected.name(),
                expected.isDisplayed(), expected.menuProductRequests()))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴의 가격이 올바르지 않으면 등록할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void create(final BigDecimal price) {
        final MenuRequest expected = createMenuRequest(
            "후라이드+후라이드", price, menuGroupId, true, createMenuProductRequest(product.id(), 2L)
        );
        assertThatThrownBy(() -> menuService.create(expected.price(), expected.menuGroupId(), expected.name(),
                expected.isDisplayed(), expected.menuProductRequests()))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.")
    @Test
    void createExpensiveMenu() {
        final MenuRequest expected = createMenuRequest(
            "후라이드+후라이드", 33_000L, menuGroupId, true, createMenuProductRequest(product.id(), 2L)
        );
        assertThatThrownBy(() -> menuService.create(expected.price(), expected.menuGroupId(), expected.name(),
                expected.isDisplayed(), expected.menuProductRequests()))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴는 특정 메뉴 그룹에 속해야 한다.")
    @NullSource
    @ParameterizedTest
    void create(final UUID menuGroupId) {
        final MenuRequest expected = createMenuRequest(
            "후라이드+후라이드", 19_000L, menuGroupId, true, createMenuProductRequest(product.id(), 2L)
        );
        assertThatThrownBy(() -> menuService.create(expected.price(), expected.menuGroupId(), expected.name(),
                expected.isDisplayed(), expected.menuProductRequests()))
            .isInstanceOf(NoSuchElementException.class);
    }

    @DisplayName("메뉴의 이름이 올바르지 않으면 등록할 수 없다.")
    @ValueSource(strings = {"비속어", "욕설이 포함된 이름"})
    @NullSource
    @ParameterizedTest
    void create(final String name) {
        final MenuRequest expected = createMenuRequest(
            name, 19_000L, menuGroupId, true, createMenuProductRequest(product.id(), 2L)
        );
        assertThatThrownBy(() -> menuService.create(expected.price(), expected.menuGroupId(), expected.name(),
                expected.isDisplayed(), expected.menuProductRequests()))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴의 가격을 변경할 수 있다.")
    @Test
    void changePrice() {
        final UUID menuId = menuRepository.save(menu(19_000L, menuProduct(product, 2L))).id();
        final BigDecimal expected = changePriceRequest(16_000L);
        final MenuModel actual = menuService.changePrice(menuId, expected);
        assertThat(actual.price()).isEqualTo(expected);
    }

    @DisplayName("메뉴의 가격이 올바르지 않으면 변경할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void changePrice(final BigDecimal expected) {
        final UUID menuId = menuRepository.save(menu(19_000L, menuProduct(product, 2L))).id();
        assertThatThrownBy(() -> menuService.changePrice(menuId, expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.")
    @Test
    void changePriceToExpensive() {
        final UUID menuId = menuRepository.save(menu(19_000L, menuProduct(product, 2L))).id();
        final BigDecimal expected = changePriceRequest(33_000L);
        assertThatThrownBy(() -> menuService.changePrice(menuId, expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴를 노출할 수 있다.")
    @Test
    void display() {
        final UUID menuId = menuRepository.save(menu(19_000L, false, menuProduct(product, 2L))).id();
        final MenuModel actual = menuService.display(menuId);
        assertThat(actual.isDisplayed()).isTrue();
    }

    @DisplayName("메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 높을 경우 메뉴를 노출할 수 없다.")
    @Test
    void displayExpensiveMenu() {
        final UUID menuId = menuRepository.save(menu(33_000L, false, menuProduct(product, 2L))).id();
        assertThatThrownBy(() -> menuService.display(menuId))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("메뉴를 숨길 수 있다.")
    @Test
    void hide() {
        final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct(product, 2L))).id();
        final MenuModel actual = menuService.hide(menuId);
        assertThat(actual.isDisplayed()).isFalse();
    }

    @DisplayName("메뉴의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        menuRepository.save(menu(19_000L, true, menuProduct(product, 2L)));
        final List<Menu> actual = menuService.findAll();
        assertThat(actual).hasSize(1);
    }

    private MenuRequest createMenuRequest(
        final String name,
        final long price,
        final UUID menuGroupId,
        final boolean displayed,
        final MenuProductRequest... menuProductRequests
    ) {
        return createMenuRequest(name, BigDecimal.valueOf(price), menuGroupId, displayed, menuProductRequests);
    }

    private MenuRequest createMenuRequest(
        final String name,
        final BigDecimal price,
        final UUID menuGroupId,
        final boolean displayed,
        final MenuProductRequest... menuProductRequests
    ) {
        return createMenuRequest(name, price, menuGroupId, displayed, Arrays.asList(menuProductRequests));
    }

    private MenuRequest createMenuRequest(
        final String name,
        final long price,
        final UUID menuGroupId,
        final boolean displayed,
        final List<MenuProductRequest> menuProductRequests
    ) {
        return createMenuRequest(name, BigDecimal.valueOf(price), menuGroupId, displayed, menuProductRequests);
    }

    private MenuRequest createMenuRequest(
        final String name,
        final BigDecimal price,
        final UUID menuGroupId,
        final boolean displayed,
        final List<MenuProductRequest> menuProductRequests
    ) {
        return new MenuRequest(price, menuGroupId, name, displayed, menuProductRequests);
    }

    private static MenuProductRequest createMenuProductRequest(final UUID productId, final long quantity) {
        return new MenuProductRequest(productId, quantity);
    }

    private BigDecimal changePriceRequest(final long price) {
        return BigDecimal.valueOf(price);
    }
}
