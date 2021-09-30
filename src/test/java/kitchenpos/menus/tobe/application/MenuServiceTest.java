package kitchenpos.menus.tobe.application;

import kitchenpos.common.domain.Profanities;
import kitchenpos.common.event.FakeEventPublisher;
import kitchenpos.common.infra.FakeProfanities;
import kitchenpos.fixture.MenuFixture;
import kitchenpos.fixture.ProductFixture;
import kitchenpos.menus.tobe.infra.InMemoryMenuGroupRepository;
import kitchenpos.menus.tobe.infra.InMemoryMenuRepository;
import kitchenpos.menus.tobe.menu.infra.RestMenuProductClient;
import kitchenpos.menus.tobe.menu.application.MenuService;
import kitchenpos.menus.tobe.menu.domain.Menu;
import kitchenpos.menus.tobe.menu.domain.MenuProductLoader;
import kitchenpos.menus.tobe.menu.domain.MenuProducts;
import kitchenpos.menus.tobe.menu.domain.MenuRepository;
import kitchenpos.menus.tobe.menugroup.application.MenuGroupService;
import kitchenpos.menus.tobe.menugroup.domain.MenuGroup;
import kitchenpos.menus.tobe.menugroup.domain.MenuGroupRepository;
import kitchenpos.menus.tobe.menu.ui.dto.MenuChangePriceRequest;
import kitchenpos.menus.tobe.menu.ui.dto.MenuCreateRequest;
import kitchenpos.menus.tobe.menu.ui.dto.MenuProductRequest;
import kitchenpos.products.tobe.application.ProductService;
import kitchenpos.products.tobe.infra.InMemoryProductRepository;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
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

@DisplayName("메뉴 응용 서비스(MenuService)는")
class MenuServiceTest {
    private MenuRepository menuRepository;
    private MenuGroupRepository menuGroupRepository;
    private ProductRepository productRepository;
    private Profanities profanities = new FakeProfanities();
    private FakeEventPublisher eventPublisher = new FakeEventPublisher();
    private MenuService menuService;
    private MenuGroup menuGroup;
    private UUID menuGroupId;
    private Product product;

    @BeforeEach
    void setUp() {
        menuRepository = new InMemoryMenuRepository();
        menuGroupRepository = new InMemoryMenuGroupRepository();
        productRepository = new InMemoryProductRepository();
        MenuGroupService menuGroupService = new MenuGroupService(menuGroupRepository);
        ProductService productService = new ProductService(productRepository, profanities, eventPublisher);
        RestMenuProductClient menuProductClient = new RestMenuProductClient(productService);
        menuService = new MenuService(menuRepository, new MenuProductLoader(), menuProductClient, menuGroupService, profanities);
        menuGroup = menuGroupRepository.save(MenuFixture.메뉴그룹());
        menuGroupId = menuGroup.getId();
        product = productRepository.save(ProductFixture.상품(16_000L));
    }

    @DisplayName("1개 이상의 등록된 상품으로 메뉴를 등록할 수 있다.")
    @Test
    void create() {
        final MenuCreateRequest expected = createMenuRequest(
            "후라이드+후라이드", 19_000L, menuGroupId, true, createMenuProductRequest(product.getId(), 2L)
        );
        final Menu actual = menuService.create(expected);
        assertThat(actual).isNotNull();
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.isDisplayed()).isEqualTo(expected.isDisplayed())
        );
    }

    @DisplayName("상품이 없으면 등록할 수 없다.")
    @MethodSource("menuProducts")
    @ParameterizedTest
    void create(final List<MenuProductRequest> menuProducts) {
        final MenuCreateRequest expected = createMenuRequest("후라이드+후라이드", 19_000L, menuGroupId, true, menuProducts);
        assertThatThrownBy(() -> menuService.create(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    private static List<Arguments> menuProducts() {
        return Arrays.asList(
            null,
            Arguments.of(Collections.emptyList()),
            Arguments.of(Arrays.asList(createMenuProductRequest(INVALID_ID, 2L)))
        );
    }

    @DisplayName("메뉴에 속한 상품의 수량(quantity)은 0개 이상이어야 한다.")
    @Test
    void createNegativeQuantity() {
        final MenuCreateRequest expected = createMenuRequest(
            "후라이드+후라이드", 19_000L, menuGroupId, true, createMenuProductRequest(product.getId(), -1L)
        );
        assertThatThrownBy(() -> menuService.create(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴의 가격(price)이 올바르지 않으면 등록할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void create(final BigDecimal price) {
        final MenuCreateRequest expected = createMenuRequest(
            "후라이드+후라이드", price, menuGroupId, true, createMenuProductRequest(product.getId(), 2L)
        );
        assertThatThrownBy(() -> menuService.create(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴의 가격(price)은 메뉴에 속한 상품 금액의 합(amount)보다 적거나 같아야 한다.")
    @Test
    void createExpensiveMenu() {
        final MenuCreateRequest expected = createMenuRequest(
            "후라이드+후라이드", 33_000L, menuGroupId, true, createMenuProductRequest(product.getId(), 2L)
        );
        assertThatThrownBy(() -> menuService.create(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴는 특정 메뉴 그룹에 속해야 한다.")
    @NullSource
    @ParameterizedTest
    void create(final UUID menuGroupId) {
        final MenuCreateRequest expected = createMenuRequest(
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
        final MenuCreateRequest expected = createMenuRequest(
            name, 19_000L, menuGroupId, true, createMenuProductRequest(product.getId(), 2L)
        );
        assertThatThrownBy(() -> menuService.create(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴의 가격을 변경할 수 있다.")
    @Test
    void changePrice() {
        final MenuProducts menuProducts = MenuFixture.금액이불러와진_메뉴상품목록(product);
        final UUID menuId = 메뉴저장하기(MenuFixture.메뉴(900L, menuGroup, menuProducts)).getId();
        final MenuChangePriceRequest expected = changePriceRequest(1_000L);
        final Menu actual = menuService.changePrice(menuId, expected);
        assertThat(actual).isEqualTo(MenuFixture.메뉴(1_000L, menuGroup, menuProducts));
    }

    @DisplayName("메뉴의 가격이 올바르지 않으면 변경할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void changePrice(final BigDecimal price) {
        final UUID menuId = 메뉴저장하기().getId();
        final MenuChangePriceRequest expected = changePriceRequest(price);
        assertThatThrownBy(() -> menuService.changePrice(menuId, expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("변경하려는 가격(price)이 메뉴 상품 금액의 합(amount)보다 크면 메뉴가 숨겨진다.")
    @Test
    void changePriceToExpensive() {
        final UUID menuId = 메뉴저장하기().getId();
        final MenuChangePriceRequest expected = changePriceRequest(33_000L);
        final Menu actual = menuService.changePrice(menuId, expected);
        assertThat(actual.isDisplayed()).isFalse();
    }

    @DisplayName("메뉴를 노출할 수 있다.")
    @Test
    void display() {
        final UUID menuId = 메뉴저장하기().getId();
        final Menu actual = menuService.display(menuId);
        assertThat(actual.isDisplayed()).isTrue();
    }

    @DisplayName("메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 높을 경우 메뉴를 노출할 수 없다.")
    @Test
    void displayExpensiveMenu() {
        final UUID menuId = 메뉴저장하기().getId();
        final MenuChangePriceRequest expected = changePriceRequest(33_000L);
        메뉴저장하기(menuService.changePrice(menuId, expected));

        assertThatThrownBy(() -> menuService.display(menuId))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("메뉴를 숨길 수 있다.")
    @Test
    void hide() {
        final UUID menuId = 메뉴저장하기().getId();
        final Menu actual = menuService.hide(menuId);
        assertThat(actual.isDisplayed()).isFalse();
    }

    @DisplayName("메뉴의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        메뉴저장하기();
        final List<Menu> actual = menuService.findAll();
        assertThat(actual).hasSize(1);
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
        final long price,
        final UUID menuGroupId,
        final boolean displayed,
        final List<MenuProductRequest> menuProducts
    ) {
        return createMenuRequest(name, BigDecimal.valueOf(price), menuGroupId, displayed, menuProducts);
    }

    private MenuCreateRequest createMenuRequest(
        final String name,
        final BigDecimal price,
        final UUID menuGroupId,
        final boolean displayed,
        final List<MenuProductRequest> menuProducts
    ) {
        return new MenuCreateRequest(name, price, menuGroupId, displayed, menuProducts);
    }

    private static MenuProductRequest createMenuProductRequest(final UUID productId, final long quantity) {
        return new MenuProductRequest(productId, quantity);
    }

    private MenuChangePriceRequest changePriceRequest(final long price) {
        return changePriceRequest(BigDecimal.valueOf(price));
    }

    private MenuChangePriceRequest changePriceRequest(final BigDecimal price) {
        return new MenuChangePriceRequest(price);
    }

    private Menu 간단메뉴() {
        return MenuFixture.메뉴(19_000L, MenuFixture.금액이불러와진_메뉴상품목록(product));
    }

    private Menu 메뉴저장하기() {
        return menuRepository.save(간단메뉴());
    }

    private Menu 메뉴저장하기(Menu menu) {
        return menuRepository.save(menu);
    }
}
