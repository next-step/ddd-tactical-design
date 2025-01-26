package kitchenpos.menus.application;

import kitchenpos.MenuFixture;
import kitchenpos.menus.application.dto.MenuCreateProductRequest;
import kitchenpos.menus.application.dto.MenuCreateRequest;
import kitchenpos.menus.application.dto.MenuPriceChangeRequest;
import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuGroupRepository;
import kitchenpos.menus.tobe.domain.MenuRepository;
import kitchenpos.menus.tobe.domain.Product;
import kitchenpos.clients.FakePurgomalumClient;
import kitchenpos.products.application.InMemoryProductRepository;
import kitchenpos.products.tobe.domain.ProductRepository;
import kitchenpos.menus.tobe.domain.PurgomalumClient;
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
import static kitchenpos.MenuFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class MenuServiceTest {
    private MenuRepository menuRepository;
    private MenuGroupRepository menuGroupRepository;
    private MenuAntiCorruptionService menuAntiCorruptionService;
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
        menuAntiCorruptionService = new MenuAntiCorruptionService(productRepository);
        menuService = new MenuService(menuRepository, menuGroupRepository, menuAntiCorruptionService, purgomalumClient);
        menuGroupId = menuGroupRepository.save(menuGroup()).getId();
        var savedProduct = productRepository.save(product());
        product = new Product(savedProduct.getId(), savedProduct.getPrice());
    }

    @DisplayName("1개 이상의 등록된 상품으로 메뉴를 등록할 수 있다.")
    @Test
    void create() {
        final MenuCreateRequest expected = createMenuRequest(
                "후라이드+후라이드", 19_000L, menuGroupId, true, createMenuProductRequest(product.getProductId(), 2L)
        );
        final Menu actual = menuService.create(expected);
        assertThat(actual).isNotNull();
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
                () -> assertThat(actual.getPrice()).isEqualTo(expected.getPrice()),
                () -> assertThat(actual.getMenuGroup().getId()).isEqualTo(expected.getMenuGroupId()),
                () -> assertThat(actual.isDisplayed()).isEqualTo(expected.isDisplayed()),
                () -> assertThat(actual.getMenuProducts()).hasSize(1)
        );
    }

    @DisplayName("상품이 없으면 등록할 수 없다.")
    @MethodSource("menuProducts")
    @ParameterizedTest
    void create(final List<MenuCreateProductRequest> menuProducts) {
        final MenuCreateRequest expected = createMenuRequest("후라이드+후라이드", 19_000L, menuGroupId, true, menuProducts);
        assertThatThrownBy(() -> menuService.create(expected))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private static List<Arguments> menuProducts() {
        return Arrays.asList(
                null,
                Arguments.of(Collections.emptyList()),
                Arguments.of(Arrays.asList(createMenuProductRequest(MenuFixture.INVALID_ID, 2L)))
        );
    }

    @DisplayName("메뉴에 속한 상품의 수량은 0개 이상이어야 한다.")
    @Test
    void createNegativeQuantity() {
        final MenuCreateRequest expected = createMenuRequest(
                "후라이드+후라이드", 19_000L, menuGroupId, true, createMenuProductRequest(product.getProductId(), -1L)
        );
        assertThatThrownBy(() -> menuService.create(expected))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴의 가격이 올바르지 않으면 등록할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void create(final BigDecimal price) {
        final MenuCreateRequest expected = createMenuRequest(
                "후라이드+후라이드", price, menuGroupId, true, createMenuProductRequest(product.getProductId(), 2L)
        );
        assertThatThrownBy(() -> menuService.create(expected))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.")
    @Test
    void createExpensiveMenu() {
        final MenuCreateRequest expected = createMenuRequest(
                "후라이드+후라이드", 33_000L, menuGroupId, true, createMenuProductRequest(product.getProductId(), 2L)
        );
        assertThatThrownBy(() -> menuService.create(expected))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴는 특정 메뉴 그룹에 속해야 한다.")
    @NullSource
    @ParameterizedTest
    void create(final UUID menuGroupId) {
        final MenuCreateRequest expected = createMenuRequest(
                "후라이드+후라이드", 19_000L, menuGroupId, true, createMenuProductRequest(product.getProductId(), 2L)
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
                name, 19_000L, menuGroupId, true, createMenuProductRequest(product.getProductId(), 2L)
        );
        assertThatThrownBy(() -> menuService.create(expected))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴의 가격을 변경할 수 있다.")
    @Test
    void changePrice() {
        final UUID menuId = menuRepository.save(menu(19_000L, menuProduct(product, 2L))).getId();
        final MenuPriceChangeRequest expected = changePriceRequest(16_000L);
        final Menu actual = menuService.changePrice(menuId, expected);
        assertThat(actual.getPrice()).isEqualTo(expected.getPrice());
    }

    @DisplayName("메뉴의 가격이 올바르지 않으면 변경할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void changePrice(final BigDecimal price) {
        final UUID menuId = menuRepository.save(menu(19_000L, menuProduct(product, 2L))).getId();
        final MenuPriceChangeRequest expected = changePriceRequest(price);
        assertThatThrownBy(() -> menuService.changePrice(menuId, expected))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.")
    @Test
    void changePriceToExpensive() {
        final UUID menuId = menuRepository.save(menu(19_000L, menuProduct(product, 2L))).getId();
        final MenuPriceChangeRequest expected = changePriceRequest(33_000L);
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

    @DisplayName("메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 높을 경우 메뉴를 노출할 수 없다.")
    @Test
    void displayExpensiveMenu() {
        final UUID menuId = menuRepository.save(menu(33_000L, false, menuProduct(product, 2L))).getId();
        assertThatThrownBy(() -> menuService.display(menuId))
                .isInstanceOf(IllegalStateException.class);
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

    private MenuCreateRequest createMenuRequest(
            final String name,
            final long price,
            final UUID menuGroupId,
            final boolean displayed,
            final MenuCreateProductRequest... menuProducts
    ) {
        return createMenuRequest(name, BigDecimal.valueOf(price), menuGroupId, displayed, menuProducts);
    }

    private MenuCreateRequest createMenuRequest(
            final String name,
            final BigDecimal price,
            final UUID menuGroupId,
            final boolean displayed,
            final MenuCreateProductRequest... menuProducts
    ) {
        return createMenuRequest(name, price, menuGroupId, displayed, Arrays.asList(menuProducts));
    }

    private MenuCreateRequest createMenuRequest(
            final String name,
            final long price,
            final UUID menuGroupId,
            final boolean displayed,
            final List<MenuCreateProductRequest> menuProducts
    ) {
        return createMenuRequest(name, BigDecimal.valueOf(price), menuGroupId, displayed, menuProducts);
    }

    private MenuCreateRequest createMenuRequest(
            final String name,
            final BigDecimal price,
            final UUID menuGroupId,
            final boolean displayed,
            final List<MenuCreateProductRequest> menuProducts
    ) {
        return new MenuCreateRequest(name, price, menuGroupId, displayed, menuProducts);
    }

    private static MenuCreateProductRequest createMenuProductRequest(final UUID productId, final long quantity) {
        return new MenuCreateProductRequest(productId, quantity);
    }

    private MenuPriceChangeRequest changePriceRequest(BigDecimal price) {
        return new MenuPriceChangeRequest(price);
    }

    private MenuPriceChangeRequest changePriceRequest(long price) {
        return new MenuPriceChangeRequest(BigDecimal.valueOf(price));
    }
}
