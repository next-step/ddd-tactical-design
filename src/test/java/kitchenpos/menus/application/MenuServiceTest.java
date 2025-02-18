package kitchenpos.menus.application;

import kitchenpos.fixture.MenuProductFixture;
import kitchenpos.menus.domain.*;
import kitchenpos.products.domain.InMemoryProductRepository;
import kitchenpos.products.domain.Product;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.infra.FakePurgomalumClient;
import kitchenpos.products.infra.PurgomalumClient;
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

import static kitchenpos.fixture.MenuFixture.*;
import static kitchenpos.fixture.MenuGroupFixture.DOUBLE_CHICKEN_MENU_GROUP_NAME;
import static kitchenpos.fixture.MenuGroupFixture.menuGroup;
import static kitchenpos.fixture.MenuProductFixture.menuProduct;
import static kitchenpos.fixture.ProductFixture.*;
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

    @BeforeEach
    void setUp() {
        menuRepository = new InMemoryMenuRepository();
        menuGroupRepository = new InMemoryMenuGroupRepository();
        productRepository = new InMemoryProductRepository();
        purgomalumClient = new FakePurgomalumClient();
        menuService = new MenuService(menuRepository, menuGroupRepository, productRepository, purgomalumClient);
        menuGroup = menuGroupRepository.save(menuGroup(DOUBLE_CHICKEN_MENU_GROUP_NAME));
        product = productRepository.save(product(FRIED_CHICKEN, FRIED_CHICKEN_PRICE));
    }

    @DisplayName("1개 이상의 등록된 상품으로 메뉴를 등록할 수 있다.")
    @Test
    void create() {
        final Menu expected = menu(
                null, DOUBLE_FRIED_CHICKEN_MENU, DOUBLE_FRIED_CHICKEN_MENU_PRICE,
                menuGroup, List.of(menuProduct(2L, product)), true
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
    void create(final List<MenuProduct> menuProducts) {
        final Menu expected = menu(
                null, DOUBLE_FRIED_CHICKEN_MENU, DOUBLE_FRIED_CHICKEN_MENU_PRICE,
                menuGroup, menuProducts, true
        );
        assertThatThrownBy(() -> menuService.create(expected))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private static List<Arguments> menuProducts() {
        return Arrays.asList(
                null,
                Arguments.of(Collections.emptyList()),
                Arguments.of(Arrays.asList(MenuProductFixture.menuProduct(2L, product())))
        );
    }

    @DisplayName("메뉴에 속한 상품의 수량은 0개 이상이어야 한다.")
    @Test
    void createNegativeQuantity() {
        final Menu expected = menu(
                null, DOUBLE_FRIED_CHICKEN_MENU, DOUBLE_FRIED_CHICKEN_MENU_PRICE,
                menuGroup, List.of(menuProduct(-1L, product)), true
        );
        assertThatThrownBy(() -> menuService.create(expected))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴의 가격이 올바르지 않으면 등록할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void create(final BigDecimal price) {
        final Menu expected = menu(
                null, DOUBLE_FRIED_CHICKEN_MENU, price,
                menuGroup, List.of(menuProduct(2L, product)), true
        );
        assertThatThrownBy(() -> menuService.create(expected))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.")
    @Test
    void createExpensiveMenu() {
        final Menu expected = menu(
                null, DOUBLE_FRIED_CHICKEN_MENU, BigDecimal.valueOf(33_000L),
                menuGroup, List.of(menuProduct(-2L, product)), true
        );
        assertThatThrownBy(() -> menuService.create(expected))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴는 특정 메뉴 그룹에 속해야 한다.")
    @NullSource
    @ParameterizedTest
    void create(final UUID menuGroupId) {
        final MenuGroup otherMenuGroup = menuGroup(menuGroupId, DOUBLE_CHICKEN_MENU_GROUP_NAME);

        assertThatThrownBy(() ->
                menu(
                        null, DOUBLE_FRIED_CHICKEN_MENU, DOUBLE_FRIED_CHICKEN_MENU_PRICE, otherMenuGroup,
                        List.of(menuProduct(2L, product)), true
                )
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴의 이름이 올바르지 않으면 등록할 수 없다.")
    @ValueSource(strings = {"비속어", "욕설이 포함된 이름"})
    @NullSource
    @ParameterizedTest
    void create(final String name) {
        final Menu expected = menu(
                null, name, DOUBLE_FRIED_CHICKEN_MENU_PRICE, menuGroup,
                List.of(menuProduct(2L, product)), true
        );
        assertThatThrownBy(() -> menuService.create(expected))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴의 가격을 변경할 수 있다.")
    @Test
    void changePrice() {
        final Menu menu = menuRepository.save(
                menu(
                        DOUBLE_FRIED_CHICKEN_MENU, DOUBLE_FRIED_CHICKEN_MENU_PRICE,
                        menuGroup, List.of(menuProduct(2L, product)), true
                )
        );
        final UUID menuId = menu.getId();
        final Menu expected = menu(
                menuId, menu.getName(), BigDecimal.valueOf(16_000L),
                menu.getMenuGroup(), menu.getMenuProducts(), menu.isDisplayed()
        );
        final Menu actual = menuService.changePrice(menuId, expected);
        assertThat(actual.getPrice()).isEqualTo(expected.getPrice());
    }

    @DisplayName("메뉴의 가격이 올바르지 않으면 변경할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void changePrice(final BigDecimal price) {
        final Menu menu = menuRepository.save(
                menu(
                        DOUBLE_FRIED_CHICKEN_MENU, DOUBLE_FRIED_CHICKEN_MENU_PRICE,
                        menuGroup, List.of(menuProduct(2L, product)), true
                )
        );
        final UUID menuId = menu.getId();
        final Menu expected = menu(
                menuId, menu.getName(), price,
                menu.getMenuGroup(), menu.getMenuProducts(), menu.isDisplayed()
        );
        assertThatThrownBy(() -> menuService.changePrice(menuId, expected))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.")
    @Test
    void changePriceToExpensive() {
        final Menu menu = menuRepository.save(
                menu(
                        DOUBLE_FRIED_CHICKEN_MENU, DOUBLE_FRIED_CHICKEN_MENU_PRICE,
                        menuGroup, List.of(menuProduct(2L, product)), true
                )
        );
        final UUID menuId = menu.getId();
        final Menu expected = menu(
                menuId, menu.getName(), BigDecimal.valueOf(33_000L),
                menu.getMenuGroup(), menu.getMenuProducts(), menu.isDisplayed()
        );
        assertThatThrownBy(() -> menuService.changePrice(menuId, expected))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴를 노출할 수 있다.")
    @Test
    void display() {
        final Menu menu = menuRepository.save(
                menu(
                        DOUBLE_FRIED_CHICKEN_MENU, DOUBLE_FRIED_CHICKEN_MENU_PRICE,
                        menuGroup, List.of(menuProduct(2L, product)), false
                )
        );
        final UUID menuId = menu.getId();
        final Menu actual = menuService.display(menuId);
        assertThat(actual.isDisplayed()).isTrue();
    }

    @DisplayName("메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 높을 경우 메뉴를 노출할 수 없다.")
    @Test
    void displayExpensiveMenu() {
        final Menu menu = menuRepository.save(
                menu(
                        DOUBLE_FRIED_CHICKEN_MENU, BigDecimal.valueOf(33_000L),
                        menuGroup, List.of(menuProduct(2L, product)), false
                )
        );
        final UUID menuId = menu.getId();
        assertThatThrownBy(() -> menuService.display(menuId))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("메뉴를 숨길 수 있다.")
    @Test
    void hide() {
        final Menu menu = menuRepository.save(
                menu(
                        DOUBLE_FRIED_CHICKEN_MENU, DOUBLE_FRIED_CHICKEN_MENU_PRICE,
                        menuGroup, List.of(menuProduct(2L, product)), true
                )
        );
        final UUID menuId = menu.getId();
        final Menu actual = menuService.hide(menuId);
        assertThat(actual.isDisplayed()).isFalse();
    }

    @DisplayName("메뉴의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        menuRepository.save(
                menu(
                        DOUBLE_FRIED_CHICKEN_MENU, DOUBLE_FRIED_CHICKEN_MENU_PRICE,
                        menuGroup, List.of(menuProduct(2L, product)), true
                )
        );
        final List<Menu> actual = menuService.findAll();
        assertThat(actual).hasSize(1);
    }
}
