package kitchenpos.menus.tobe.application;

import static kitchenpos.Fixtures.INVALID_ID;
import static kitchenpos.Fixtures.menu;
import static kitchenpos.Fixtures.menuGroup;
import static kitchenpos.Fixtures.menuProduct;
import static kitchenpos.Fixtures.product;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import kitchenpos.common.purgomalum.FakePurgomalumClient;
import kitchenpos.common.purgomalum.PurgomalumClient;
import kitchenpos.menus.tobe.domain.application.CalculateSumOfMultiplyingMenuProductPriceAndMenuProductQuantity;
import kitchenpos.menus.tobe.domain.application.CalculateSumOfMultiplyingMenuProductPriceAndMenuProductQuantityTestFixture;
import kitchenpos.menus.tobe.domain.application.ChangeMenuPriceTestFixture;
import kitchenpos.menus.tobe.domain.application.CreateMenuGroupTestFixture;
import kitchenpos.menus.tobe.domain.application.CreateMenuTestFixture;
import kitchenpos.menus.tobe.domain.application.DisplayMenuTestFixture;
import kitchenpos.menus.tobe.domain.application.HideMenuTextFixture;
import kitchenpos.menus.tobe.domain.entity.Menu;
import kitchenpos.menus.tobe.domain.repository.InMemoryMenuRepository;
import kitchenpos.menus.tobe.domain.repository.MenuRepository;
import kitchenpos.menus.tobe.dto.MenuChangePriceDto;
import kitchenpos.menus.tobe.dto.MenuCreateDto;
import kitchenpos.menus.tobe.dto.MenuProductCreateDto;
import kitchenpos.products.tobe.domain.entity.Product;
import kitchenpos.products.tobe.domain.repository.InMemoryProductRepository;
import kitchenpos.products.tobe.domain.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class MenuCommandHandlerTest {
    private MenuRepository menuRepository;
    private ProductRepository productRepository;
    private PurgomalumClient purgomalumClient;
    private MenuCommandHandler menuCommandHandler;
    private UUID menuGroupId;
    private Product product;
    private CalculateSumOfMultiplyingMenuProductPriceAndMenuProductQuantity calculateSumOfMultiplyingMenuProductPriceAndMenuProductQuantity;

    @BeforeEach
    void setUp() {
        menuRepository = new InMemoryMenuRepository();
        productRepository = new InMemoryProductRepository();
        purgomalumClient = new FakePurgomalumClient();
        calculateSumOfMultiplyingMenuProductPriceAndMenuProductQuantity = new CalculateSumOfMultiplyingMenuProductPriceAndMenuProductQuantityTestFixture(productRepository);
        menuCommandHandler = new MenuCommandHandler(menuRepository,
                                                    new CreateMenuTestFixture(menuRepository, productRepository, purgomalumClient),
                                                    new ChangeMenuPriceTestFixture(menuRepository, productRepository),
                                                    new DisplayMenuTestFixture(menuRepository, productRepository),
                                                    new HideMenuTextFixture(menuRepository),
                                                    new CreateMenuGroupTestFixture(menuRepository)
        );
        menuGroupId = menuRepository.saveMenuGroup(menuGroup()).getId();
        product = productRepository.save(product("후라이드", 16_000L));
    }

    @DisplayName("1개 이상의 등록된 상품으로 메뉴를 등록할 수 있다.")
    @Test
    void createMenu() {
        final MenuCreateDto expected = createMenuMenuRequest(
            "후라이드+후라이드", 19_000L, menuGroupId, true, Arrays.asList(createMenuMenuProductRequest(product.getId(), 2L))
        );
        final Menu actual = menuCommandHandler.createMenu(expected);
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
    void createMenu(final List<MenuProductCreateDto> menuProducts) {
        final MenuCreateDto expected = createMenuMenuRequest("후라이드+후라이드", 19_000L, menuGroupId, true, menuProducts);
        assertThatThrownBy(() -> menuCommandHandler.createMenu(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    private static List<Arguments> menuProducts() {
        return Arrays.asList(
            null,
            Arguments.of(Collections.emptyList()),
            Arguments.of(Arrays.asList(createMenuMenuProductRequest(INVALID_ID, 2L)))
        );
    }

    @DisplayName("메뉴에 속한 상품의 수량은 0개 이상이어야 한다.")
    @Test
    void createMenuNegativeQuantity() {
        final MenuCreateDto expected = createMenuMenuRequest(
            "후라이드+후라이드", 19_000L, menuGroupId, true, Arrays.asList(createMenuMenuProductRequest(product.getId(), -1L))
        );
        assertThatThrownBy(() -> menuCommandHandler.createMenu(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴의 가격이 올바르지 않으면 등록할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void createMenu(final BigDecimal price) {
        final MenuCreateDto expected = createMenuMenuRequest(
            "후라이드+후라이드", price, menuGroupId, true, Arrays.asList(createMenuMenuProductRequest(product.getId(), 2L))
        );
        assertThatThrownBy(() -> menuCommandHandler.createMenu(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.")
    @Test
    void createMenuExpensiveMenu() {
        final MenuCreateDto expected = createMenuMenuRequest(
            "후라이드+후라이드", 33_000L, menuGroupId, true, Arrays.asList(createMenuMenuProductRequest(product.getId(), 2L))
        );
        assertThatThrownBy(() -> menuCommandHandler.createMenu(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴는 특정 메뉴 그룹에 속해야 한다.")
    @NullSource
    @ParameterizedTest
    void createMenu(final UUID menuGroupId) {
        final MenuCreateDto expected = createMenuMenuRequest(
            "후라이드+후라이드", 19_000L, menuGroupId, true, Arrays.asList(createMenuMenuProductRequest(product.getId(), 2L))
        );
        assertThatThrownBy(() -> menuCommandHandler.createMenu(expected))
            .isInstanceOf(NoSuchElementException.class);
    }

    @DisplayName("메뉴의 이름이 올바르지 않으면 등록할 수 없다.")
    @ValueSource(strings = {"비속어", "욕설이 포함된 이름"})
    @NullSource
    @ParameterizedTest
    void createMenu(final String name) {
        final MenuCreateDto expected = createMenuMenuRequest(
            name, 19_000L, menuGroupId, true, Arrays.asList(createMenuMenuProductRequest(product.getId(), 2L))
        );
        assertThatThrownBy(() -> menuCommandHandler.createMenu(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴의 가격을 변경할 수 있다.")
    @Test
    void changePrice() {
        final UUID menuId = menuRepository.saveMenu(menu(19_000L, menuProduct(product, 2L))).getId();
        final MenuChangePriceDto expected = changePriceRequest(16_000L);
        final Menu actual = menuCommandHandler.changePrice(menuId, expected);
        assertThat(actual.getPrice()).isEqualTo(expected.getPrice());
    }

    @DisplayName("메뉴의 가격이 올바르지 않으면 변경할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void changePrice(final BigDecimal price) {
        final UUID menuId = menuRepository.saveMenu(menu(19_000L, menuProduct(product, 2L))).getId();
        final MenuChangePriceDto expected = changePriceRequest(price);
        assertThatThrownBy(() -> menuCommandHandler.changePrice(menuId, expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.")
    @Test
    void changePriceToExpensive() {
        final UUID menuId = menuRepository.saveMenu(menu(19_000L, menuProduct(product, 2L))).getId();
        final MenuChangePriceDto expected = changePriceRequest(33_000L);
        assertThatThrownBy(() -> menuCommandHandler.changePrice(menuId, expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴를 노출할 수 있다.")
    @Test
    void display() {
        final UUID menuId = menuRepository.saveMenu(menu(19_000L, false, menuProduct(product, 2L))).getId();
        final Menu actual = menuCommandHandler.display(menuId);
        assertThat(actual.isDisplayed()).isTrue();
    }

    @DisplayName("메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 높을 경우 메뉴를 노출할 수 없다.")
    @Test
    void displayExpensiveMenu() {
        final UUID menuId = menuRepository.saveMenu(menu(33_000L, false, menuProduct(product, 2L))).getId();
        assertThatThrownBy(() -> menuCommandHandler.display(menuId))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("메뉴를 숨길 수 있다.")
    @Test
    void hide() {
        final UUID menuId = menuRepository.saveMenu(menu(19_000L, true, menuProduct(product, 2L))).getId();
        final Menu actual = menuCommandHandler.hide(menuId);
        assertThat(actual.isDisplayed()).isFalse();
    }


    private MenuCreateDto createMenuMenuRequest(
        final String name,
        final long price,
        final UUID menuGroupId,
        final boolean displayed,
        final List<MenuProductCreateDto> menuProducts
    ) {
        return createMenuMenuRequest(name, BigDecimal.valueOf(price), menuGroupId, displayed, menuProducts);
    }

    private MenuCreateDto createMenuMenuRequest(
        final String name,
        final BigDecimal price,
        final UUID menuGroupId,
        final boolean displayed,
        final List<MenuProductCreateDto> menuProducts
    ) {
        final MenuCreateDto request = new MenuCreateDto(
                name, price, displayed, menuGroupId, menuProducts
        );
        return request;
    }

    private static MenuProductCreateDto createMenuMenuProductRequest(final UUID productId, final long quantity) {
        final MenuProductCreateDto menuProduct = new MenuProductCreateDto(productId, quantity);
        return menuProduct;
    }

    private MenuChangePriceDto changePriceRequest(final long price) {
        return changePriceRequest(BigDecimal.valueOf(price));
    }

    private MenuChangePriceDto changePriceRequest(final BigDecimal price) {
        return new MenuChangePriceDto(price);
    }
}
