package kitchenpos.menus.application;

import kitchenpos.menus.dto.MenuChangePriceRequest;
import kitchenpos.menus.dto.MenuCreateRequest;
import kitchenpos.menus.dto.MenuDetailResponse;
import kitchenpos.menus.dto.MenuProductElement;
import kitchenpos.menus.mapper.MenuMapper;
import kitchenpos.menus.tobe.domain.menu.*;
import kitchenpos.menus.tobe.domain.menugroup.MenuGroup;
import kitchenpos.menus.tobe.domain.menugroup.MenuGroupRepository;
import kitchenpos.products.infra.FakePurgomalumClient;
import kitchenpos.products.tobe.domain.InMemoryProductRepository;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import kitchenpos.support.infra.PurgomalumClient;
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

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toUnmodifiableList;
import static kitchenpos.Fixtures.*;
import static kitchenpos.menus.tobe.domain.menu.FakeMenu.createFake;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class MenuServiceTest {
    private MenuRepository menuRepository;
    private MenuGroupRepository menuGroupRepository;
    private ProductRepository productRepository;
    private PurgomalumClient purgomalumClient;
    private MenuService menuService;
    private MenuGroup menuGroup;
    private Product product;

    private static List<Arguments> menuProducts() {
        return Arrays.asList(
                null,
                Arguments.of(Collections.emptyList())
        );
    }

    private static MenuProduct createMenuProductRequest(final Product product, final long quantity) {
        return MenuProduct.create(product, quantity);
    }

    @BeforeEach
    void setUp() {
        menuRepository = new InMemoryMenuRepository();
        menuGroupRepository = new InMemoryMenuGroupRepository();
        productRepository = new InMemoryProductRepository();
        purgomalumClient = new FakePurgomalumClient();
        menuService = new MenuService(menuRepository, menuGroupRepository, productRepository, purgomalumClient);
        menuGroup = menuGroupRepository.save(menuGroup());
        product = productRepository.save(product("후라이드", 16_000L));
    }

    @DisplayName("1개 이상의 등록된 상품으로 메뉴를 등록할 수 있다.")
    @Test
    void create() {
        final Menu expected = createMenuRequest(
                "후라이드+후라이드", 19_000L, menuGroup, true, createMenuProductRequest(product, 2L)
        );
        MenuDetailResponse actual = menuService.create(new MenuCreateRequest(
                expected.getName(),
                expected.getPrice(),
                expected.getMenuGroupId(),
                expected.isDisplayed(),
                collectMenuProductElements(expected.getMenuProducts())
        ));
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

    private List<MenuProductElement> collectMenuProductElements(List<MenuProduct> menuProducts) {
        if (isNull(menuProducts)) {
            return null;
        }

        return menuProducts
                .stream()
                .map(MenuMapper::toMenuProductElement)
                .collect(toUnmodifiableList());
    }

    @DisplayName("상품이 없으면 등록할 수 없다.")
    @MethodSource("menuProducts")
    @ParameterizedTest
    void create(final List<MenuProduct> menuProducts) {
        assertThatThrownBy(() -> createMenuRequest("후라이드+후라이드", 19_000L, menuGroup, true, menuProducts))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("존재하지 않는 상품이 있으면 등록할 수 없다.")
    @Test
    void createNotExistedMenuProducts() {
        final List<MenuProduct> notExistedProduct = List.of(createMenuProductRequest(product(), 2L));
        final Menu expected = createMenuRequest("후라이드+후라이드", 19_000L, menuGroup, true, notExistedProduct);
        assertThatThrownBy(() -> menuService.create(new MenuCreateRequest(expected.getName(),
                expected.getPrice(),
                menuGroup.getId(),
                expected.isDisplayed(),
                collectMenuProductElements(expected.getMenuProducts()))))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴에 속한 상품의 수량은 0개 이상이어야 한다.")
    @Test
    void createNegativeQuantity() {
        assertThatThrownBy(() -> createMenuProductRequest(product, -1L))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴의 가격이 올바르지 않으면 등록할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void create(final BigDecimal price) {
        assertThatThrownBy(() -> createMenuRequest(
                "후라이드+후라이드", price, menuGroup, true, createMenuProductRequest(product, 2L)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.")
    @Test
    void createExpensiveMenu() {
        assertThatThrownBy(() -> createMenuRequest(
                "후라이드+후라이드", 33_000L, menuGroup, true, createMenuProductRequest(product, 2L)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴는 특정 메뉴 그룹에 속해야 한다.")
    @NullSource
    @ParameterizedTest
    void create(final UUID menuGroupId) {
        final Menu expected = createMenuRequest(
                "후라이드+후라이드", 19_000L, menuGroup, true, createMenuProductRequest(product, 2L)
        );
        assertThatThrownBy(() -> menuService.create(new MenuCreateRequest(expected.getName(),
                expected.getPrice(),
                menuGroupId,
                expected.isDisplayed(),
                collectMenuProductElements(expected.getMenuProducts()))))
                .isInstanceOf(NoSuchElementException.class);
    }

    @DisplayName("메뉴의 이름이 올바르지 않으면 등록할 수 없다.")
    @ValueSource(strings = {"비속어", "욕설이 포함된 이름"})
    @NullSource
    @ParameterizedTest
    void create(final String name) {
        assertThatThrownBy(() -> createMenuRequest(
                name, 19_000L, menuGroup, true, createMenuProductRequest(product, 2L)
        )).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴의 가격을 변경할 수 있다.")
    @Test
    void changePrice() {
        final UUID menuId = menuRepository.save(menu(19_000L, menuProduct(product, 2L))).getId();
        final BigDecimal expected = BigDecimal.valueOf(16_000L);
        final MenuDetailResponse actual = menuService.changePrice(menuId, new MenuChangePriceRequest(expected));
        assertThat(actual.getPrice()).isEqualTo(expected);
    }

    @DisplayName("메뉴의 가격이 올바르지 않으면 변경할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void changePrice(final BigDecimal expectedPrice) {
        final UUID menuId = menuRepository.save(menu(19_000L, menuProduct(product, 2L))).getId();

        assertThatThrownBy(() -> menuService.changePrice(menuId, new MenuChangePriceRequest(expectedPrice)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.")
    @Test
    void changePriceToExpensive() {
        final UUID menuId = menuRepository.save(menu(19_000L, menuProduct(product, 2L))).getId();
        final BigDecimal expected = BigDecimal.valueOf(33_000L);
        assertThatThrownBy(() -> menuService.changePrice(menuId, new MenuChangePriceRequest(expected)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴를 노출할 수 있다.")
    @Test
    void display() {
        final UUID menuId = menuRepository.save(menu(19_000L, false, menuProduct(product, 2L))).getId();
        final MenuDetailResponse actual = menuService.display(menuId);
        assertThat(actual.isDisplayed()).isTrue();
    }

    @DisplayName("메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 높을 경우 메뉴를 노출할 수 없다.")
    @Test
    void displayExpensiveMenu() {
        final UUID menuId = menuRepository.save(createFake(
                "치킨 메뉴",
                purgomalumClient,
                33_000L,
                menuGroup,
                false,
                List.of(menuProduct(product, 2L))
        )).getId();
        assertThatThrownBy(() -> menuService.display(menuId))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("메뉴를 숨길 수 있다.")
    @Test
    void hide() {
        final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct(product, 2L))).getId();
        final MenuDetailResponse actual = menuService.hide(menuId);
        assertThat(actual.isDisplayed()).isFalse();
    }

    @DisplayName("메뉴의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        menuRepository.save(menu(19_000L, true, menuProduct(product, 2L)));
        final List<MenuDetailResponse> actual = menuService.findAll();
        assertThat(actual).hasSize(1);
    }

    private Menu createMenuRequest(
            final String name,
            final long price,
            final MenuGroup menuGroup,
            final boolean displayed,
            final MenuProduct... menuProducts
    ) {
        return createMenuRequest(name, BigDecimal.valueOf(price), menuGroup, displayed, menuProducts);
    }

    private Menu createMenuRequest(
            final String name,
            final BigDecimal price,
            final MenuGroup menuGroup,
            final boolean displayed,
            final MenuProduct... menuProducts
    ) {
        return createMenuRequest(name, price, menuGroup, displayed, Arrays.asList(menuProducts));
    }

    private Menu createMenuRequest(
            final String name,
            final long price,
            final MenuGroup menuGroup,
            final boolean displayed,
            final List<MenuProduct> menuProducts
    ) {
        return createMenuRequest(name, BigDecimal.valueOf(price), menuGroup, displayed, menuProducts);
    }

    private Menu createMenuRequest(
            final String name,
            final BigDecimal price,
            final MenuGroup menuGroup,
            final boolean displayed,
            final List<MenuProduct> menuProducts
    ) {
        return Menu.create(
                MenuName.create(name, purgomalumClient),
                MenuPrice.create(price),
                menuGroup,
                MenuDisplay.create(displayed),
                menuProducts
        );
    }

}
