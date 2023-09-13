package kitchenpos.apply.menu.tobe.application;

import kitchenpos.apply.menu.tobe.domain.InMemoryMenuGroupRepository;
import kitchenpos.apply.menu.tobe.domain.InMemoryMenuRepository;
import kitchenpos.apply.menu.tobe.infra.FakePurgomalumClient;
import kitchenpos.apply.menus.tobe.application.DefaultMenuPriceChecker;
import kitchenpos.apply.menus.tobe.application.MenuService;
import kitchenpos.apply.menus.tobe.domain.MenuGroupRepository;
import kitchenpos.apply.menus.tobe.domain.MenuPriceChecker;
import kitchenpos.apply.menus.tobe.domain.MenuRepository;
import kitchenpos.apply.menus.tobe.ui.MenuProductRequest;
import kitchenpos.apply.menus.tobe.ui.MenuRequest;
import kitchenpos.apply.menus.tobe.ui.MenuResponse;
import kitchenpos.apply.products.tobe.application.ProductValidator;
import kitchenpos.apply.products.tobe.domain.InMemoryProductRepository;
import kitchenpos.apply.products.tobe.domain.Product;
import kitchenpos.apply.products.tobe.domain.ProductRepository;
import kitchenpos.support.domain.PurgomalumClient;
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

import static kitchenpos.apply.fixture.MenuFixture.*;
import static kitchenpos.apply.fixture.TobeFixtures.product;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class MenuServiceTest {
    private MenuRepository menuRepository;
    private MenuService menuService;
    private UUID menuGroupId;
    private Product product;

    @BeforeEach
    void setUp() {
        MenuGroupRepository menuGroupRepository = new InMemoryMenuGroupRepository();
        ProductRepository productRepository = new InMemoryProductRepository();
        PurgomalumClient purgomalumClient = new FakePurgomalumClient();
        MenuPriceChecker priceChecker = new DefaultMenuPriceChecker(productRepository, menuRepository);
        ProductValidator productValidator = new ProductValidator(productRepository);
        menuRepository = new InMemoryMenuRepository();
        menuService = new MenuService(menuRepository, menuGroupRepository, priceChecker, purgomalumClient, productValidator);
        menuGroupId = UUID.fromString(menuGroupRepository.save(menuGroup()).getId());
        product = productRepository.save(product("후라이드", 16_000L));
    }

    @DisplayName("1개 이상의 등록된 상품으로 메뉴를 등록할 수 있다.")
    @Test
    void create() {
        final MenuRequest request = menuRequest(
            "후라이드+후라이드", 19_000L, menuGroupId, true, menuProductRequest(product, 2L)
        );
        final MenuResponse response = menuService.create(request);
        assertThat(response).isNotNull();
        assertAll(
            () -> assertThat(response.getId()).isNotNull(),
            () -> assertThat(response.getName()).isEqualTo(request.getName()),
            () -> assertThat(response.getPrice()).isEqualTo(request.getPrice()),
            () -> assertThat(response.getMenuGroupId()).isEqualTo(request.getMenuGroupId().toString()),
            () -> assertThat(response.isDisplayed()).isEqualTo(request.isDisplayed()),
            () -> assertThat(response.getMenuProductsSeq()).hasSize(1)
        );
    }

    @DisplayName("상품이 없으면 등록할 수 없다.")
    @MethodSource("menuProducts")
    @ParameterizedTest
    void create(final List<MenuProductRequest> menuProductRequests) {
        final MenuRequest request = menuRequest("후라이드+후라이드", BigDecimal.valueOf(19_000L), menuGroupId, true, menuProductRequests);
        assertThatThrownBy(() -> menuService.create(request))
            .isInstanceOf(IllegalArgumentException.class);
    }

    private static List<Arguments> menuProducts() {
        return Arrays.asList(
            null,
            Arguments.of(Collections.emptyList()),
            Arguments.of(List.of(menuProductRequest(INVALID_ID, 2L)))
        );
    }

    @DisplayName("메뉴에 속한 상품의 수량은 0개 이상이어야 한다.")
    @Test
    void createNegativeQuantity() {
        final MenuRequest request = menuRequest(
            "후라이드+후라이드", 19_000L, menuGroupId, true, menuProductRequest(product, -1L)
        );
        assertThatThrownBy(() -> menuService.create(request))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴의 가격이 올바르지 않으면 등록할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void create(final BigDecimal price) {
        final MenuRequest request = menuRequest(
            "후라이드+후라이드", price, menuGroupId, true, menuProductRequest(product, 2L)
        );
        assertThatThrownBy(() -> menuService.create(request))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.")
    @Test
    void createExpensiveMenu() {
        final MenuRequest request = menuRequest(
            "후라이드+후라이드", 33_000L, menuGroupId, true, menuProductRequest(product, 2L)
        );
        assertThatThrownBy(() -> menuService.create(request))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴는 특정 메뉴 그룹에 속해야 한다.")
    @NullSource
    @ParameterizedTest
    void create(final UUID menuGroupId) {
        final MenuRequest request = menuRequest(
            "후라이드+후라이드", 19_000L, menuGroupId, true, menuProductRequest(product, 2L)
        );
        assertThatThrownBy(() -> menuService.create(request))
            .isInstanceOf(NoSuchElementException.class);
    }

    @DisplayName("메뉴의 이름이 올바르지 않으면 등록할 수 없다.")
    @ValueSource(strings = {"비속어", "욕설이 포함된 이름"})
    @NullSource
    @ParameterizedTest
    void create(final String name) {
        final MenuRequest request = menuRequest(
            name, 19_000L, menuGroupId, true, menuProductRequest(product, 2L)
        );
        assertThatThrownBy(() -> menuService.create(request))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴의 가격을 변경할 수 있다.")
    @Test
    void changePrice() {
        final UUID menuId = UUID.fromString(menuRepository.save(menu(19_000L, menuProduct(product, 2L))).getId());
        final MenuRequest request = menuRequest(16_000L);
        final MenuResponse response = menuService.changePrice(menuId, request);
        assertThat(response.getPrice()).isEqualTo(request.getPrice());
    }

    @DisplayName("메뉴의 가격이 올바르지 않으면 변경할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void changePrice(final BigDecimal price) {
        final UUID menuId = UUID.fromString(menuRepository.save(menu(19_000L, menuProduct(product, 2L))).getId());
        final MenuRequest request = menuRequest(price);
        assertThatThrownBy(() -> menuService.changePrice(menuId, request))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.")
    @Test
    void changePriceToExpensive() {
        final UUID menuId = UUID.fromString(menuRepository.save(menu(19_000L, menuProduct(product, 2L))).getId());
        final MenuRequest request = menuRequest(33_000L);
        assertThatThrownBy(() -> menuService.changePrice(menuId, request))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴를 노출할 수 있다.")
    @Test
    void display() {
        final UUID menuId = UUID.fromString(menuRepository.save(menu(19_000L, false, menuProduct(product, 2L))).getId());
        final MenuResponse response = menuService.display(menuId);
        assertThat(response.isDisplayed()).isTrue();
    }

    @DisplayName("메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 높을 경우 메뉴를 노출할 수 없다.")
    @Test
    void displayExpensiveMenu() {
        final UUID menuId = UUID.fromString(menuRepository.save(menu(33_000L, false, menuProduct(product, 2L))).getId());
        assertThatThrownBy(() -> menuService.display(menuId))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("메뉴를 숨길 수 있다.")
    @Test
    void hide() {
        final UUID menuId = UUID.fromString(menuRepository.save(menu(19_000L, true, menuProduct(product, 2L))).getId());
        final MenuResponse response = menuService.hide(menuId);
        assertThat(response.isDisplayed()).isFalse();
    }
}
