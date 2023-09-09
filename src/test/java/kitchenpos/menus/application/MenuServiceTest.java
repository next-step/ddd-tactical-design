package kitchenpos.menus.application;

import kitchenpos.menus.tobe.application.ToBeMenuService;
import kitchenpos.menus.tobe.domain.ToBeMenu;
import kitchenpos.menus.tobe.domain.ToBeMenuGroupRepository;
import kitchenpos.menus.tobe.domain.ToBeMenuProduct;
import kitchenpos.menus.tobe.domain.ToBeMenuRepository;
import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.application.InMemoryProductRepository;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.ToBeProduct;
import kitchenpos.products.tobe.domain.ToBeProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.*;

import static kitchenpos.Fixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class MenuServiceTest {
    private ToBeMenuRepository toBeMenuRepository;
    private ToBeMenuGroupRepository menuGroupRepository;
    private ToBeProductRepository toBeProductRepository;
    private PurgomalumClient purgomalumClient;
    private ToBeMenuService menuService;
    private UUID menuGroupId;
    private ToBeProduct product;

    @BeforeEach
    void setUp() {
        toBeMenuRepository = new InMemoryMenuRepository();
        menuGroupRepository = new InMemoryMenuGroupRepository();
        toBeProductRepository = new InMemoryProductRepository();
        purgomalumClient = new FakePurgomalumClient();
        menuService = new ToBeMenuService(toBeMenuRepository, menuGroupRepository, toBeProductRepository, purgomalumClient);
        menuGroupId = menuGroupRepository.save(menuGroup()).getId();
        product = toBeProductRepository.save(new ToBeProduct("후라이드", BigDecimal.valueOf(16_000L),purgomalumClient));
    }

    @DisplayName("1개 이상의 등록된 상품으로 메뉴를 등록할 수 있다.")
    @Test
    void create() {
        final ToBeMenu expected = createMenuRequest("후라이드+후라이드", BigDecimal.valueOf(19_000L),UUID.randomUUID(), true, createMenuProductRequest(product.getId(), 2L));

            final ToBeMenu actual = menuService.create(expected);
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
    void create(final List<ToBeMenuProduct> menuProducts) {
        final ToBeMenu expected = createMenuRequest("후라이드+후라이드", 19_000L, menuGroupId, true, menuProducts);
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

    @DisplayName("메뉴에 속한 상품의 수량은 0개 이상이어야 한다.")
    @Test
    void createNegativeQuantity() {
        assertThatThrownBy(() -> createMenuRequest("후라이드+후라이드", 19_000L, menuGroupId, true, createMenuProductRequest(product.getId(), -1L))).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴의 가격이 올바르지 않으면 등록할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void create(final BigDecimal price) {
        final ToBeMenu expected = createMenuRequest(
            "후라이드+후라이드", price, menuGroupId, true, createMenuProductRequest(product.getId(), 2L)
        );
        assertThatThrownBy(() -> menuService.create(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.")
    @Test
    void createExpensiveMenu() {
        final ToBeMenu expected = createMenuRequest(
            "후라이드+후라이드", 33_000L, menuGroupId, true, createMenuProductRequest(product.getId(), 2L)
        );
        assertThatThrownBy(() -> menuService.create(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴는 특정 메뉴 그룹에 속해야 한다.")
    @NullSource
    @ParameterizedTest
    void create(final UUID menuGroupId) {
        final ToBeMenu expected = createMenuRequest(
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
        final ToBeMenu expected = createMenuRequest(
            name, 19_000L, menuGroupId, true, createMenuProductRequest(product.getId(), 2L)
        );
        assertThatThrownBy(() -> menuService.create(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴의 가격을 변경할 수 있다.")
    @Test
    void changePrice() {
        final UUID menuId = toBeMenuRepository.save(menu(19_000L, purgomalumClient,menuProduct(product, 2L))).getId();
        final ToBeMenu expected = changePriceRequest(16_000L);
        final ToBeMenu actual = menuService.changePrice(menuId, expected);
        assertThat(actual.getPrice()).isEqualTo(expected.getPrice());
    }

    @DisplayName("메뉴의 가격이 올바르지 않으면 변경할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void changePrice(final BigDecimal price) {
        final UUID menuId = toBeMenuRepository.save(menu(19_000L, purgomalumClient,menuProduct(product, 2L))).getId();
        assertThatThrownBy(() -> changePriceRequest(price))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.")
    @Test
    void changePriceToExpensive() {
        final UUID menuId = toBeMenuRepository.save(menu(19_000L, purgomalumClient,menuProduct(product, 2L))).getId();
        final ToBeMenu expected = changePriceRequest(33_000L);
        assertThatThrownBy(() -> menuService.changePrice(menuId, expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴를 노출할 수 있다.")
    @Test
    void display() {
        final UUID menuId = toBeMenuRepository.save(menu(19_000L, false,purgomalumClient, menuProduct(product, 2L))).getId();
        final ToBeMenu actual = menuService.display(menuId);
        assertThat(actual.isDisplayed()).isTrue();
    }

    @DisplayName("메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 높을 경우 메뉴를 노출할 수 없다.")
    @Test
    void displayExpensiveMenu() {
        final UUID menuId = toBeMenuRepository.save(menu(33_000L, false, purgomalumClient,menuProduct(product, 2L))).getId();
        assertThatThrownBy(() -> menuService.display(menuId))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("메뉴를 숨길 수 있다.")
    @Test
    void hide() {
        final UUID menuId = toBeMenuRepository.save(menu(19_000L, true, purgomalumClient,menuProduct(product, 2L))).getId();
        final ToBeMenu actual = menuService.hide(menuId);
        assertThat(actual.isDisplayed()).isFalse();
    }

    @DisplayName("메뉴의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        toBeMenuRepository.save(menu(19_000L, true, purgomalumClient, menuProduct(product, 2L)));
        final List<ToBeMenu> actual = menuService.findAll();
        assertThat(actual).hasSize(1);
    }

    private ToBeMenu createMenuRequest(
        final String name,
        final long price,
        final UUID menuGroupId,
        final boolean displayed,
        final ToBeMenuProduct... menuProducts
    ) {
        return createMenuRequest(name, BigDecimal.valueOf(price), menuGroupId, displayed, menuProducts);
    }

    private ToBeMenu createMenuRequest(
        final String name,
        final BigDecimal price,
        final UUID menuGroupId,
        final boolean displayed,
        final ToBeMenuProduct... menuProducts
    ) {
        return createMenuRequest(name, price, menuGroupId, displayed, Arrays.asList(menuProducts));
    }

    private ToBeMenu createMenuRequest(
        final String name,
        final long price,
        final UUID menuGroupId,
        final boolean displayed,
        final List<ToBeMenuProduct> menuProducts
    ) {
        return createMenuRequest(name, BigDecimal.valueOf(price), menuGroupId, displayed, menuProducts);
    }

    private ToBeMenu createMenuRequest(
        final String name,
        final BigDecimal price,
        final UUID menuGroupId,
        final boolean displayed,
        final List<ToBeMenuProduct> menuProducts
    ) {
        final ToBeMenu menu = new ToBeMenu(name,price.longValue(),menuGroupId,displayed,purgomalumClient,menuProducts);
        return menu;
    }

    private static ToBeMenuProduct createMenuProductRequest(final UUID productId, final long quantity) {
        final ToBeMenuProduct menuProduct = new ToBeMenuProduct(productId, quantity);
        return menuProduct;
    }

    private ToBeMenu changePriceRequest(final long price) {
        return changePriceRequest(BigDecimal.valueOf(price));
    }

    private ToBeMenu changePriceRequest(final BigDecimal price) {
        final ToBeMenu menu = new ToBeMenu();
        menu.changePrice(price);
        return menu;
    }
}
