package kitchenpos.menus.application;

import kitchenpos.common.domain.Name;
import kitchenpos.common.domain.Price;
import kitchenpos.common.domain.PurgomalumClient;
import kitchenpos.menus.application.menu.MenuService;
import kitchenpos.menus.application.menu.dto.MenuProductRequest;
import kitchenpos.menus.application.menu.dto.MenuRequest;
import kitchenpos.menus.application.menu.dto.MenuResponse;
import kitchenpos.menus.domain.menu.MenuProduct;
import kitchenpos.menus.domain.menu.MenuProducts;
import kitchenpos.menus.domain.menu.MenuRepository;
import kitchenpos.menus.domain.menu.ProductClient;
import kitchenpos.menus.domain.menugroup.MenuGroupRepository;
import kitchenpos.menus.infra.DefaultProductClient;
import kitchenpos.products.application.InMemoryProductRepository;
import kitchenpos.products.domain.Product;
import kitchenpos.products.domain.ProductRepository;
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
    private ProductClient productClient;
    private MenuService menuService;
    private UUID menuGroupId;
    private Product product;

    @BeforeEach
    void setUp() {
        menuRepository = new InMemoryMenuRepository();
        menuGroupRepository = new InMemoryMenuGroupRepository();
        productRepository = new InMemoryProductRepository();
        purgomalumClient = new FakeMenuPurgomalumClient();
        productClient = new DefaultProductClient(productRepository);
        menuService = new MenuService(menuRepository, menuGroupRepository, purgomalumClient, productClient);
        menuGroupId = menuGroupRepository.save(menuGroup()).getId();
        product = productRepository.save(product("후라이드", 16_000L));
    }

    @DisplayName("1개 이상의 등록된 상품으로 메뉴를 등록할 수 있다.")
    @Test
    void create() {
        final MenuRequest expected = createMenuRequest(
                "후라이드+후라이드", 19_000L, menuGroupId, true, createMenuProductRequest(product.getId(), 2L)
        );
        final MenuResponse actual = menuService.create(expected);
        assertThat(actual).isNotNull();
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
                () -> assertThat(actual.getPrice()).isEqualTo(expected.getPrice()),
                () -> assertThat(actual.getMenuGroupId()).isEqualTo(expected.getMenuGroupId()),
                () -> assertThat(actual.isDisplayed()).isEqualTo(expected.isDisplayed()),
                () -> assertThat(actual.getMenuProductsResponse()).hasSize(1)
        );
    }

    @DisplayName("상품이 없으면 등록할 수 없다.")
    @MethodSource("menuProducts")
    @ParameterizedTest
    void create(final List<MenuProductRequest> menuProducts) {
        MenuRequest expected = createMenuRequest("후라이드+후라이드", 19_000L, menuGroupId, true, menuProducts);
        assertThatThrownBy(() -> menuService.create(expected))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private static List<Arguments> menuProducts() {
        return Arrays.asList(
                null,
                Arguments.of(Collections.emptyList()),
                Arguments.of(List.of(createMenuProductRequest(INVALID_ID, 2L)))
        );
    }

    @DisplayName("메뉴에 속한 상품의 수량은 0개 이상이어야 한다.")
    @Test
    void createNegativeQuantity() {
        final MenuRequest expected = createMenuRequest(
                "후라이드+후라이드", 19_000L, menuGroupId, true, createMenuProductRequest(product.getId(), -1L)
        );
        assertThatThrownBy(() -> menuService.create(expected))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴의 가격이 올바르지 않으면 등록할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void create(final BigDecimal price) {
        final MenuRequest expected = createMenuRequest(
                "후라이드+후라이드", price, menuGroupId, true, createMenuProductRequest(product.getId(), 2L)
        );
        assertThatThrownBy(() -> menuService.create(expected))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.")
    @Test
    void createExpensiveMenu() {
        final MenuRequest expected = createMenuRequest(
                "후라이드+후라이드", 33_000L, menuGroupId, true, createMenuProductRequest(product.getId(), 2L)
        );
        assertThatThrownBy(() -> menuService.create(expected))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴는 특정 메뉴 그룹에 속해야 한다.")
    @NullSource
    @ParameterizedTest
    void create(final UUID menuGroupId) {
        final MenuRequest expected = createMenuRequest(
                "후라이드+후라이드", 19_000L, menuGroupId, true, createMenuProductRequest(product.getId(), 2L)
        );
        assertThatThrownBy(() -> menuService.create(expected))
                .isInstanceOf(NoSuchElementException.class);
    }

    @DisplayName("메뉴의 이름이 올바르지 않으면 등록할 수 없다.")
    @ValueSource(strings = {"비속어", "욕설이 포함된 이름"})
    @NullSource
    @ParameterizedTest
    void create(final String name) {
        final MenuRequest expected = createMenuRequest(
                name, 19_000L, menuGroupId, true, createMenuProductRequest(product.getId(), 2L)
        );
        assertThatThrownBy(() -> menuService.create(expected))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴의 가격을 변경할 수 있다.")
    @Test
    void changePrice() {
        final UUID menuId = menuRepository.save(menu(19_000L, menuProduct(product, 2L))).getId();
        final MenuRequest expected = changePriceRequest(16_000L);
        final MenuResponse actual = menuService.changePrice(menuId, expected);
        assertThat(actual.getPrice()).isEqualTo(expected.getPrice());
    }

    @DisplayName("메뉴의 가격이 올바르지 않으면 변경할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void changePrice(final BigDecimal price) {
        final UUID menuId = menuRepository.save(menu(19_000L, menuProduct(product, 2L))).getId();
        final MenuRequest expected = changePriceRequest(price);
        assertThatThrownBy(() -> menuService.changePrice(menuId, expected))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.")
    @Test
    void changePriceToExpensive() {
        final UUID menuId = menuRepository.save(menu(19_000L, menuProduct(product, 2L))).getId();
        final MenuRequest expected = changePriceRequest(33_000L);
        assertThatThrownBy(() -> menuService.changePrice(menuId, expected))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴를 노출할 수 있다.")
    @Test
    void display() {
        final UUID menuId = menuRepository.save(menu(19_000L, false, menuProduct(product, 2L))).getId();
        final MenuResponse actual = menuService.display(menuId);
        assertThat(actual.isDisplayed()).isTrue();
    }

    @DisplayName("메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 높을 경우 메뉴를 노출할 수 없다.")
    @Test
    void displayExpensiveMenu() {
        final MenuProduct menuProduct = menuProduct(product, 2L);
        final UUID menuId = menuRepository.save(menu(19_000L, false, menuProduct)).getId();
        menuProduct.changeProductPrice(Price.of(BigDecimal.valueOf(9_000L)));
        assertThatThrownBy(() -> menuService.display(menuId))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("메뉴를 숨길 수 있다.")
    @Test
    void hide() {
        final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct(product, 2L))).getId();
        final MenuResponse actual = menuService.hide(menuId);
        assertThat(actual.isDisplayed()).isFalse();
    }

    @DisplayName("메뉴의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        menuRepository.save(menu(19_000L, true, menuProduct(product, 2L)));
        final List<MenuResponse> actual = menuService.findAll();
        assertThat(actual).hasSize(1);
    }

    @DisplayName("Price VO를 정상 생성한다.")
    @ValueSource(strings = "16000")
    @ParameterizedTest
    void createPriceVo(final BigDecimal price) {
        final Price actual = Price.of(price);
        assertThat(actual.getPrice()).isEqualTo(price);
    }

    @DisplayName("Price VO 생성시 가격이 비어 있거나 0원 이하면 예외가 발생한다.")
    @ValueSource(strings = "-10000")
    @NullSource
    @ParameterizedTest
    void throwExceptionOfPriceVo(final BigDecimal price) {
        assertThatThrownBy(() -> Price.of(price))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("Name VO를 정상 생성한다.")
    @ValueSource(strings = "간장치킨")
    @ParameterizedTest
    void createName(final String name) {
        final Name actual = Name.of(name, purgomalumClient);
        assertThat(actual.getName()).isEqualTo(name);
    }

    @DisplayName("Name VO 생성시 이름이 비어 있거나 비속어가 포함되어 있으면 예외가 발생한다.")
    @ValueSource(strings = "비속어")
    @NullSource
    @ParameterizedTest
    void throwExceptionOfName(final String name) {
        assertThatThrownBy(() -> Name.of(name, purgomalumClient))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("MenuProducts 일급 컬렉션을 정상 생성한다.")
    @Test
    void createMenuProducts() {
        final List<MenuProduct> expected = List.of(menuProduct(product, 2L));
        final MenuProducts actual = MenuProducts.of(expected);
        assertThat(actual.getMenuProducts()).isEqualTo(expected);
    }

    @DisplayName("MenuProducts 일급 컬렉션 생성시 MenuProduct List가 비어 있으면 예외가 발생한다.")
    @MethodSource("menuProducts")
    @NullSource
    @ParameterizedTest
    void throwExceptionOfEmptyMenuProducts(final List<MenuProduct> menuProducts) {
        final List<MenuProduct> expected =  Collections.emptyList();
        assertThatThrownBy(() -> MenuProducts.of(expected))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("MenuProducts 일급 컬렉션 생성시 상품 수량이 0보다 작으면 예외가 발생한다.")
    @Test
    void throwExceptionOfMenuProducts() {
        final List<MenuProduct> expected = List.of(menuProduct(product, -1L));
        assertThatThrownBy(() -> MenuProducts.of(expected))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private MenuRequest createMenuRequest(
            final String name,
            final long price,
            final UUID menuGroupId,
            final boolean displayed,
            final List<MenuProductRequest> menuProductRequests
    ) {
        return new MenuRequest(name, BigDecimal.valueOf(price), menuGroupId, displayed, menuProductRequests);
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
        return new MenuRequest(name, price, menuGroupId, displayed, Arrays.asList(menuProductRequests));
    }

    private static MenuProductRequest createMenuProductRequest(final UUID productId, final long quantity) {
        return new MenuProductRequest(productId, quantity);
    }

    private MenuRequest changePriceRequest(final long price) {
        return changePriceRequest(BigDecimal.valueOf(price));
    }

    private MenuRequest changePriceRequest(final BigDecimal price) {
        return new MenuRequest(null, price, null, false, null);
    }
}
