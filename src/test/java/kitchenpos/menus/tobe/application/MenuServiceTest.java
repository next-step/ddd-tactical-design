package kitchenpos.menus.tobe.application;

import kitchenpos.common.domain.Name;
import kitchenpos.common.domain.Price;
import kitchenpos.menus.tobe.domain.*;
import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.application.InMemoryProductRepository;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

import java.math.BigDecimal;
import java.util.*;

import static kitchenpos.Fixtures.INVALID_ID;
import static kitchenpos.menus.tobe.domain.MenuFixtures.menu;
import static kitchenpos.menus.tobe.domain.MenuGroupFixtures.menuGroup;
import static kitchenpos.menus.tobe.domain.MenuProductFixtures.menuProduct;
import static kitchenpos.products.tobe.domain.ProductFixtures.product;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

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
        menuGroupId = menuGroupRepository.save(menuGroup("두마리메뉴")).getId();
        product = productRepository.save(product(purgomalumClient));
    }

    @DisplayName("1개 이상의 등록된 상품으로 메뉴를 등록할 수 있다.")
    @Test
    void create() {
        final Menu expected = new Menu(new Name("후라이드+후라이드", purgomalumClient), new Price(BigDecimal.valueOf(19000L)), menuGroupId, true, Arrays.asList(createMenuProductRequest(product.getId(), 2L)));
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
        final Menu expected = new Menu(new Name("후라이드+후라이드", purgomalumClient), new Price(BigDecimal.valueOf(19000L)), menuGroupId, true, menuProducts);
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

    @DisplayName("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.")
    @Test
    void createExpensiveMenu() {
        final Menu expected = new Menu(new Name("후라이드+후라이드", purgomalumClient), new Price(BigDecimal.valueOf(33000L)), menuGroupId, true, Arrays.asList(createMenuProductRequest(product.getId(), 2L)));
        assertThatThrownBy(() -> menuService.create(expected))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴는 특정 메뉴 그룹에 속해야 한다.")
    @NullSource
    @ParameterizedTest
    void create(final UUID menuGroupId) {
        final Menu expected = new Menu(new Name("후라이드+후라이드", purgomalumClient), new Price(BigDecimal.valueOf(19000L)), menuGroupId, true, Arrays.asList(createMenuProductRequest(product.getId(), 2L)));
        assertThatThrownBy(() -> menuService.create(expected))
                .isInstanceOf(NoSuchElementException.class);
    }

    @DisplayName("메뉴의 가격을 변경할 수 있다.")
    @Test
    void changePrice() {
        final UUID menuId = menuRepository.save(menu(new Name("후라이드+후라이드", purgomalumClient), 19000L, false, menuProduct(product, new Quantity(2L)))).getId();
        final Menu expected = new Menu(new Price(BigDecimal.valueOf(16000L)));
        final Menu actual = menuService.changePrice(menuId, expected);
        assertThat(actual.getPrice()).isEqualTo(expected.getPrice());
    }
    @DisplayName("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.")
    @Test
    void changePriceToExpensive() {
        final UUID menuId = menuRepository.save(menu(new Name("후라이드+후라이드", purgomalumClient), 19000L, false, menuProduct(product, new Quantity(2L)))).getId();
        final Menu expected = new Menu(new Price(BigDecimal.valueOf(33000L)));
        assertThatThrownBy(() -> menuService.changePrice(menuId, expected))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴를 노출할 수 있다.")
    @Test
    void display() {
        final UUID menuId = menuRepository.save(menu(new Name("후라이드+후라이드", purgomalumClient), 19000L, false, menuProduct(product, new Quantity(2L)))).getId();
        final Menu actual = menuService.display(menuId);
        assertThat(actual.isDisplayed()).isTrue();
    }

    @DisplayName("메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 높을 경우 메뉴를 노출할 수 없다.")
    @Test
    void displayExpensiveMenu() {
        final UUID menuId = menuRepository.save(menu(new Name("후라이드+후라이드", purgomalumClient), 33000L, false, menuProduct(product, new Quantity(2L)))).getId();
        assertThatThrownBy(() -> menuService.display(menuId))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴를 숨길 수 있다.")
    @Test
    void hide() {
        final UUID menuId = menuRepository.save(menu(new Name("후라이드+후라이드", purgomalumClient), 19000L, true, menuProduct(product, new Quantity(2L)))).getId();
        final Menu actual = menuService.hide(menuId);
        assertThat(actual.isDisplayed()).isFalse();
    }

    @DisplayName("메뉴의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        menuRepository.save(menu(new Name("후라이드+후라이드", purgomalumClient), 19000L, true, menuProduct(product, new Quantity(2L))));
        final List<Menu> actual = menuService.findAll();
        assertThat(actual).hasSize(1);
    }

    private static MenuProduct createMenuProductRequest(final UUID productId, final long quantity) {
        final MenuProduct menuProduct = new MenuProduct(productId, new Quantity(quantity));
        return menuProduct;
    }
}
