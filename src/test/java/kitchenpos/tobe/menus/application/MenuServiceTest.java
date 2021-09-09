package kitchenpos.tobe.menus.application;

import static kitchenpos.tobe.Fixtures.INVALID_ID;
import static kitchenpos.tobe.Fixtures.menu;
import static kitchenpos.tobe.Fixtures.menuGroup;
import static kitchenpos.tobe.Fixtures.menuProduct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import kitchenpos.menus.tobe.application.MenuService;
import kitchenpos.menus.tobe.domain.interfaces.PurgomalumClient;
import kitchenpos.menus.tobe.domain.model.Menu;
import kitchenpos.menus.tobe.domain.model.Product;
import kitchenpos.menus.tobe.domain.repository.MenuGroupRepository;
import kitchenpos.menus.tobe.domain.repository.MenuRepository;
import kitchenpos.menus.tobe.domain.repository.ProductRepository;
import kitchenpos.menus.tobe.domain.service.MenuDomainService;
import kitchenpos.menus.tobe.ui.dto.MenuRequest;
import kitchenpos.menus.tobe.ui.dto.MenuRequest.ChangePrice;
import kitchenpos.menus.tobe.ui.dto.MenuRequest.Create;
import kitchenpos.menus.tobe.ui.dto.MenuRequest.MenuProductRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class MenuServiceTest {
    private MenuRepository menuRepository;
    private MenuGroupRepository menuGroupRepository;
    private ProductRepository productRepository;
    private PurgomalumClient purgomalumClient;
    private MenuDomainService menuDomainService;
    private MenuService menuService;
    private UUID menuGroupId;
    private Product product;

    @BeforeEach
    void setUp() {
        menuRepository = new InMemoryMenuRepository();
        menuGroupRepository = new InMemoryMenuGroupRepository();
        productRepository = new FakeProductRepository();
        purgomalumClient = new FakePurgomalumClient();
        menuDomainService = new MenuDomainService(purgomalumClient, productRepository, menuGroupRepository);

        menuService = new MenuService(menuRepository, menuDomainService);
        menuGroupId = menuGroupRepository.save(menuGroup()).getId();
        product = new Product(UUID.randomUUID(), "후라이드", BigDecimal.valueOf(16_000L));
    }

    @DisplayName("1개 이상의 등록된 상품으로 메뉴를 등록할 수 있다.")
    @Test
    void create() {
        final MenuRequest.Create expected = createMenuRequest(
                "후라이드+후라이드",
                19_000L,
                menuGroupId,
                true,
                createMenuProductRequest(product.getId(), 2L)
        );

        final Menu actual = menuService.create(expected);
        assertThat(actual).isNotNull();
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getDisplayedName()).isEqualTo(expected.getDisplayName()),
            () -> assertThat(actual.getPrice()).isEqualTo(expected.getMenuPrice()),
            () -> assertThat(actual.getMenuGroupId()).isEqualTo(expected.getMenugroupId()),
            () -> assertThat(actual.isDisplayed()).isEqualTo(expected.isDisplayed()),
            () -> assertThat(actual.getMenuProducts()).hasSize(1)
        );
    }

    @DisplayName("상품이 없으면 등록할 수 없다.")
    @MethodSource("menuProducts")
    @ParameterizedTest
    void create(final List<MenuProductRequest> menuProducts) {
        final MenuRequest.Create expected = createMenuRequest("후라이드+후라이드", 19_000L, menuGroupId, true, menuProducts);
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
        final MenuRequest.Create expected = createMenuRequest(
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
        final MenuRequest.Create expected = createMenuRequest(
            "후라이드+후라이드", price, menuGroupId, true, createMenuProductRequest(product.getId(), 2L)
        );
        assertThatThrownBy(() -> menuService.create(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.")
    @Test
    void createExpensiveMenu() {
        final MenuRequest.Create expected = createMenuRequest(
            "후라이드+후라이드", 40_000L, menuGroupId, true, createMenuProductRequest(product.getId(), 2L)
        );
        assertThatThrownBy(() -> menuService.create(expected))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("메뉴는 특정 메뉴 그룹에 속해야 한다.")
    @NullSource
    @ParameterizedTest
    void create(final UUID menuGroupId) {
        final MenuRequest.Create expected = createMenuRequest(
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
        final MenuRequest.Create expected = createMenuRequest(
            name, 19_000L, menuGroupId, true, createMenuProductRequest(product.getId(), 2L)
        );
        assertThatThrownBy(() -> menuService.create(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴의 가격을 변경할 수 있다.")
    @Test
    void changePrice() {
        final UUID menuId = menuRepository.save(menu(19_000L, menuGroupId, menuDomainService, menuProduct(product, 2L))).getId();
        final ChangePrice expected = changePriceRequest(16_000L);
        final Menu actual = menuService.changePrice(menuId, expected);
        assertThat(actual.getPrice()).isEqualTo(expected.getPrice());
    }

    @DisplayName("메뉴의 가격이 올바르지 않으면 변경할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void changePrice(final BigDecimal price) {
        final UUID menuId = menuRepository.save(menu(19_000L, menuGroupId, menuDomainService, menuProduct(product, 2L))).getId();
        final ChangePrice expected = changePriceRequest(price);
        assertThatThrownBy(() -> menuService.changePrice(menuId, expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.")
    @Test
    void changePriceToExpensive() {
        final UUID menuId = menuRepository.save(menu(19_000L, menuGroupId, menuDomainService, menuProduct(product, 2L))).getId();
        final ChangePrice expected = changePriceRequest(40_000L);
        assertThatThrownBy(() -> menuService.changePrice(menuId, expected))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("메뉴를 노출할 수 있다.")
    @Test
    void display() {
        final UUID menuId = menuRepository.save(menu(19_000L, false, menuGroupId, menuDomainService, menuProduct(product, 2L))).getId();
        final Menu actual = menuService.display(menuId);
        assertThat(actual.isDisplayed()).isTrue();
    }

    @DisplayName("메뉴를 숨길 수 있다.")
    @Test
    void hide() {
        final UUID menuId = menuRepository.save(menu(19_000L, true, menuGroupId, menuDomainService, menuProduct(product, 2L))).getId();
        final Menu actual = menuService.hide(menuId);
        assertThat(actual.isDisplayed()).isFalse();
    }

    @DisplayName("메뉴의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        menuRepository.save(menu(19_000L, true, menuGroupId, menuDomainService, menuProduct(product, 2L)));
        final List<Menu> actual = menuService.findAll();
        assertThat(actual).hasSize(1);
    }

    private Create createMenuRequest(
        final String name,
        final long price,
        final UUID menuGroupId,
        final boolean displayed,
        final MenuProductRequest... menuProductRequests
    ) {
        return createMenuRequest(name, BigDecimal.valueOf(price), menuGroupId, displayed, menuProductRequests);
    }

    private Create createMenuRequest(
        final String name,
        final BigDecimal price,
        final UUID menuGroupId,
        final boolean displayed,
        final MenuProductRequest... menuProductRequests
    ) {
        return createMenuRequest(name, price, menuGroupId, displayed, Arrays.asList(menuProductRequests));
    }

    private Create createMenuRequest(
        final String name,
        final long price,
        final UUID menuGroupId,
        final boolean displayed,
        final List<MenuProductRequest> menuProductRequests
    ) {
        return createMenuRequest(name, BigDecimal.valueOf(price), menuGroupId, displayed, menuProductRequests);
    }

    private Create createMenuRequest(
        final String name,
        final BigDecimal price,
        final UUID menuGroupId,
        final boolean displayed,
        final List<MenuProductRequest> menuProductRequests
    ) {
        return new MenuRequest.Create(name, menuGroupId, price, menuProductRequests, displayed);
    }

    private static MenuProductRequest createMenuProductRequest(final UUID productId, final long quantity) {
        return new MenuProductRequest(productId, quantity);
    }

    private ChangePrice changePriceRequest(final long price) {
        return changePriceRequest(BigDecimal.valueOf(price));
    }

    private ChangePrice changePriceRequest(final BigDecimal price) {
        return new MenuRequest.ChangePrice(price);
    }
}
