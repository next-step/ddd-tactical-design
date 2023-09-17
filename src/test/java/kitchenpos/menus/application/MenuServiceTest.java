package kitchenpos.menus.application;

import kitchenpos.common.FakeApplicationEventPublisher;
import kitchenpos.common.FakeProfanityPolicy;
import kitchenpos.common.domain.Price;
import kitchenpos.common.domain.ProfanityPolicy;
import kitchenpos.common.exception.PriceException;
import kitchenpos.menugroups.application.InMemoryMenuGroupRepository;
import kitchenpos.menugroups.domain.MenuGroupRepository;
import kitchenpos.menus.dto.MenuChangePriceRequest;
import kitchenpos.menus.dto.MenuCreateRequest;
import kitchenpos.menus.dto.MenuProductRequest;
import kitchenpos.menus.dto.MenuResponse;
import kitchenpos.menus.exception.MenuDisplayedNameException;
import kitchenpos.menus.exception.MenuException;
import kitchenpos.menus.exception.MenuProductException;
import kitchenpos.menus.exception.MenuProductQuantityException;
import kitchenpos.menus.infra.DefaultMenuGroupLoader;
import kitchenpos.menus.infra.DefaultProductPriceLoader;
import kitchenpos.menus.tobe.domain.menu.MenuId;
import kitchenpos.menus.tobe.domain.menu.MenuRepository;
import kitchenpos.menus.tobe.domain.menu.ProductId;
import kitchenpos.products.application.InMemoryProductRepository;
import kitchenpos.products.application.ProductService;
import kitchenpos.products.tobe.domain.Product;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.*;

import static kitchenpos.menugroups.fixtures.MenuGroupFixture.menuGroup;
import static kitchenpos.menus.application.fixtures.MenuFixture.menu;
import static kitchenpos.menus.application.fixtures.MenuFixture.menuProduct;
import static kitchenpos.products.fixture.ProductFixture.INVALID_ID;
import static kitchenpos.products.fixture.ProductFixture.product;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("메뉴")
class MenuServiceTest {
    private MenuRepository menuRepository;
    private MenuService menuService;
    private UUID menuGroupId;
    private ProductId productId;
    private Price productPrice;


    @BeforeEach
    void setUp() {
        menuRepository = new InMemoryMenuRepository();
        MenuGroupRepository menuGroupRepository = new InMemoryMenuGroupRepository();
        kitchenpos.products.tobe.domain.ProductRepository productRepository = new InMemoryProductRepository();
        MenuGroupLoader menuGroupLoader = new DefaultMenuGroupLoader(menuGroupRepository);
        ProfanityPolicy profanityPolicy = new FakeProfanityPolicy();
        ProductService productService = new ProductService(productRepository, profanityPolicy, new FakeApplicationEventPublisher());
        DefaultProductPriceLoader productPriceLoader = new DefaultProductPriceLoader(productService);
        menuService = new MenuService(menuRepository, menuGroupLoader, productPriceLoader, profanityPolicy);
        menuGroupId = menuGroupRepository.save(menuGroup()).getIdValue();
        Product product = productRepository.save(product("후라이드", 16_000L));
        productId = new ProductId(product.getIdValue());
        productPrice = product.getPrice();
    }

    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DisplayName("메뉴 등록")
    @Nested
    class create {

        @DisplayName("1개 이상의 등록된 상품으로 메뉴를 등록할 수 있다.")
        @Test
        void create1() {
            final MenuCreateRequest expected = createMenuRequest(
                    "후라이드+후라이드", 19_000L, menuGroupId, true, createMenuProduct(productId, 2L)
            );
            final MenuResponse actual = menuService.create(expected);
            assertThat(actual).isNotNull();
            assertAll(
                    () -> assertThat(actual.getId()).isNotNull(),
                    () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
                    () -> assertThat(actual.getPrice()).isEqualTo(expected.getPrice()),
                    () -> assertThat(actual.getMenuGroupId()).isEqualTo(expected.getMenuGroupId()),
                    () -> assertThat(actual.isDisplayed()).isEqualTo(expected.isDisplayed()),
                    () -> assertThat(actual.getMenuProducts()).hasSize(1)
            );
        }

        @DisplayName("상품이 없으면 등록할 수 없다.")
        @MethodSource("menuProductRequests")
        @ParameterizedTest
        void create2(final List<MenuProductRequest> menuProductRequests) {
            final MenuCreateRequest expected = createMenuRequest("후라이드+후라이드",
                    19_000L,
                    menuGroupId,
                    true,
                    menuProductRequests);
            assertThatThrownBy(() -> menuService.create(expected))
                    .isInstanceOf(MenuProductException.class);
        }

        private List<Arguments> menuProductRequests() {
            return Arrays.asList(
                    null,
                    Arguments.of(Collections.emptyList())
            );
        }

        @DisplayName("등록되지 않은 상품은 등록할 수 없다.")
        @Test
        void create3() {
            final MenuCreateRequest expected = createMenuRequest("후라이드+후라이드",
                    19_000L,
                    menuGroupId,
                    true,
                    createMenuProduct(new ProductId(INVALID_ID), 2L));
            assertThatThrownBy(() -> menuService.create(expected))
                    .isInstanceOf(NoSuchElementException.class);
        }

        @DisplayName("메뉴에 속한 상품의 수량은 0개 이상이어야 한다.")
        @Test
        void createNegativeQuantity() {
            final MenuCreateRequest expected = createMenuRequest(
                    "후라이드+후라이드", 19_000L, menuGroupId, true, createMenuProduct(productId, -1L)
            );
            assertThatThrownBy(() -> menuService.create(expected))
                    .isInstanceOf(MenuProductQuantityException.class);
        }

        @DisplayName("메뉴의 가격이 올바르지 않으면 등록할 수 없다.")
        @ValueSource(strings = "-1000")
        @NullSource
        @ParameterizedTest
        void create4(final BigDecimal price) {
            final MenuCreateRequest expected = createMenuRequest(
                    "후라이드+후라이드", price, menuGroupId, true, createMenuProduct(productId, 2L)
            );
            assertThatThrownBy(() -> menuService.create(expected))
                    .isInstanceOf(PriceException.class);
        }

        @DisplayName("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.")
        @Test
        void createExpensiveMenu() {
            final MenuCreateRequest expected = createMenuRequest(
                    "후라이드+후라이드", 33_000L, menuGroupId, true, createMenuProduct(productId, 2L)
            );
            assertThatThrownBy(() -> menuService.create(expected))
                    .isInstanceOf(MenuException.class);
        }

        @DisplayName("메뉴는 특정 메뉴 그룹에 속해야 한다.")
        @NullSource
        @ParameterizedTest
        void create5(final UUID menuGroupId) {
            final MenuCreateRequest expected = createMenuRequest(
                    "후라이드+후라이드", 19_000L, menuGroupId, true, createMenuProduct(productId, 2L)
            );
            assertThatThrownBy(() -> menuService.create(expected))
                    .isInstanceOf(NoSuchElementException.class);
        }

        @DisplayName("메뉴의 이름이 올바르지 않으면 등록할 수 없다.")
        @ValueSource(strings = {"비속어", "욕설이 포함된 이름"})
        @NullSource
        @ParameterizedTest
        void create6(final String name) {
            final MenuCreateRequest expected = createMenuRequest(
                    name, 19_000L, menuGroupId, true, createMenuProduct(productId, 2L)
            );
            assertThatThrownBy(() -> menuService.create(expected))
                    .isInstanceOf(MenuDisplayedNameException.class);
        }
    }

    @DisplayName("메뉴의 가격을 변경할 수 있다.")
    @Test
    void changePrice() {
        final MenuId menuId = menuRepository.save(menu(19_000L, menuProduct(productId, productPrice, 2L))).getId();
        final MenuChangePriceRequest expected = new MenuChangePriceRequest(16_000L);
        final MenuResponse actual = menuService.changePrice(menuId, expected);
        assertThat(actual.getPrice()).isEqualTo(expected.getPrice());
    }

    @DisplayName("메뉴의 가격이 올바르지 않으면 변경할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void changePrice(final BigDecimal price) {
        final MenuId menuId = menuRepository.save(menu(19_000L, menuProduct(productId, productPrice, 2L))).getId();
        final MenuChangePriceRequest expected = new MenuChangePriceRequest(price);
        assertThatThrownBy(() -> menuService.changePrice(menuId, expected))
                .isInstanceOf(PriceException.class);
    }

    @DisplayName("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.")
    @Test
    void changePriceToExpensive() {
        final MenuId menuId = menuRepository.save(menu(19_000L, menuProduct(productId, productPrice, 2L))).getId();
        final MenuChangePriceRequest expected = new MenuChangePriceRequest(50_000L);
        assertThatThrownBy(() -> menuService.changePrice(menuId, expected))
                .isInstanceOf(MenuException.class);
    }

    @DisplayName("메뉴를 노출할 수 있다.")
    @Test
    void display() {
        final MenuId menuId = menuRepository.save(menu(19_000L, false, menuProduct(productId, productPrice, 2L))).getId();
        final MenuResponse actual = menuService.display(menuId);
        assertThat(actual.isDisplayed()).isTrue();
    }

    @DisplayName("메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 높을 경우 메뉴를 노출할 수 없다.")
    @Test
    void displayExpensiveMenu() {
        final MenuId menuId = menuRepository.save(menu(19_000L, false, menuProduct(productId, productPrice, 2L))).getId();
        menuService.changePrice(menuId, new MenuChangePriceRequest(33_000L));
        assertThatThrownBy(() -> menuService.display(menuId))
                .isInstanceOf(MenuException.class);
    }

    @DisplayName("메뉴를 숨길 수 있다.")
    @Test
    void hide() {
        final MenuId menuId = menuRepository.save(menu(19_000L, true, menuProduct(productId, productPrice, 2L))).getId();
        final MenuResponse actual = menuService.hide(menuId);
        assertThat(actual.isDisplayed()).isFalse();
    }

    @DisplayName("메뉴의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        menuRepository.save(menu(19_000L, true, menuProduct(productId, productPrice, 2L)));
        final List<MenuResponse> actual = menuService.findAll();
        assertThat(actual).hasSize(1);
    }

    private MenuCreateRequest createMenuRequest(String name, long price, UUID menuGroupId, boolean displayed, List<MenuProductRequest> menuProducts) {
        return createMenuRequest(name, BigDecimal.valueOf(price), menuGroupId, displayed, menuProducts);
    }

    private MenuCreateRequest createMenuRequest(
            final String name,
            final long price,
            final UUID menuGroupId,
            final boolean displayed,
            final MenuProductRequest... menuProducts
    ) {
        return createMenuRequest(name, BigDecimal.valueOf(price), menuGroupId, displayed, menuProducts);
    }


    private MenuCreateRequest createMenuRequest(
            final String name,
            final BigDecimal price,
            final UUID menuGroupId,
            final boolean displayed,
            final MenuProductRequest... menuProducts
    ) {
        return createMenuRequest(name, price, menuGroupId, displayed, Arrays.asList(menuProducts));
    }

    private MenuCreateRequest createMenuRequest(
            final String name,
            final BigDecimal price,
            final UUID menuGroupId,
            final boolean displayed,
            final MenuProductRequest menuProduct
    ) {
        return createMenuRequest(name, price, menuGroupId, displayed, Arrays.asList(menuProduct));
    }

    private MenuCreateRequest createMenuRequest(
            final String name,
            final BigDecimal price,
            final UUID menuGroupId,
            final boolean displayed,
            final List<MenuProductRequest> menuProducts
    ) {
        return new MenuCreateRequest(
                price,
                menuGroupId,
                menuProducts,
                name,
                displayed
        );
    }


    private static MenuProductRequest createMenuProduct(ProductId productId, long quantity) {
        return new MenuProductRequest(productId, quantity);
    }


}
