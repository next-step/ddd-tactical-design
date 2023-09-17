package kitchenpos.menus.tobe.application;

import kitchenpos.menus.tobe.application.dto.TobeMenuCreateRequest;
import kitchenpos.menus.tobe.application.dto.TobeMenuCreateResponse;
import kitchenpos.menus.tobe.application.dto.TobeMenuProductRequest;
import kitchenpos.menus.tobe.domain.menu.MenuPrice;
import kitchenpos.menus.tobe.domain.menu.PurgomalumChecker;
import kitchenpos.menus.tobe.domain.menu.TobeMenu;
import kitchenpos.menus.tobe.domain.menu.TobeMenuRepository;
import kitchenpos.menus.tobe.domain.menugroup.TobeMenuGroupRepository;
import kitchenpos.products.tobe.application.InMemoryProductRepository;
import kitchenpos.products.tobe.domain.TobeProduct;
import kitchenpos.products.tobe.domain.TobeProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static kitchenpos.TobeFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class TobeMenuServiceTest {
    private TobeMenuRepository menuRepository;
    private TobeMenuGroupRepository menuGroupRepository;
    private TobeProductRepository productRepository;
    private PurgomalumChecker purgomalumChecker;
    private TobeMenuService menuService;
    private UUID menuGroupId;
    private TobeProduct product;

    @BeforeEach
    void setUp() {
        menuRepository = new InMemoryTobeMenuRepository();
        menuGroupRepository = new InMemoryTobeMenuGroupRepository();
        productRepository = new InMemoryProductRepository();
        purgomalumChecker = new FakeMenuPurgomalumChecker();
        menuService = new TobeMenuService(menuRepository, menuGroupRepository, productRepository, purgomalumChecker);
        menuGroupId = menuGroupRepository.save(menuGroup()).getId();
        product = productRepository.save(product("후라이드", 16_000L));
    }

    @DisplayName("1개 이상의 등록된 상품으로 메뉴를 등록할 수 있다.")
    @Test
    void create() {
        TobeMenuCreateRequest request = createMenuRequest("후라이드+후라이드", 19_000L, menuGroupId, true,
                                                              createMenuProductRequest(product.getId(), 2L));
        TobeMenuCreateResponse response = menuService.create(request);
        assertThat(request).isNotNull();
        assertAll(
                () -> assertThat(response.getId()).isNotNull(),
                () -> assertThat(response.getName()).isEqualTo(request.getName()),
                () -> assertThat(response.getPrice()).isEqualTo(request.getPrice()),
                () -> assertThat(response.getMenuGroup().getId()).isEqualTo(request.getMenuGroupId()),
                () -> assertThat(response.isDisplayed()).isEqualTo(request.isDisplayed()),
                () -> assertThat(response.getMenuProducts()).hasSize(1)
        );
    }

    @DisplayName("상품이 없으면 등록할 수 없다.")
    @MethodSource("menuProducts")
    @ParameterizedTest
    void create(final List<TobeMenuProductRequest> menuProducts) {
        TobeMenuCreateRequest request = createMenuRequest("후라이드+후라이드", 19_000L, menuGroupId, true, menuProducts);
        assertThatThrownBy(() -> menuService.create(request))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private static List<Arguments> menuProducts() {
        return Arrays.asList(
                null,
                Arguments.of(Collections.emptyList()),
                Arguments.of(List.of(createMenuProductRequest(INVALID_ID, 2L)))
        );
    }

    @DisplayName("메뉴에 속한 상품의 수량은 0개 이상이어야 한다.")
    @Test
    void createNegativeQuantity() {
        TobeMenuCreateRequest request = createMenuRequest("후라이드+후라이드", 19_000L, menuGroupId,
                                                          true, createMenuProductRequest(product.getId(), -1L)
        );
        assertThatThrownBy(() -> menuService.create(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("메뉴 상품 수량은 0보다 작을 수 없습니다.");
    }

    @DisplayName("메뉴의 가격이 올바르지 않으면 등록할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void create(final BigDecimal price) {
        final TobeMenuCreateRequest expected = createMenuRequest(
                "후라이드+후라이드", price, menuGroupId, true, createMenuProductRequest(product.getId(), 2L)
        );
        assertThatThrownBy(() -> menuService.create(expected))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("메뉴의 가격은 0원 이상이어야 합니다.");
    }

    @DisplayName("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.")
    @Test
    void createExpensiveMenu() {
        final TobeMenuCreateRequest expected = createMenuRequest(
                "후라이드+후라이드", 33_000L, menuGroupId, true, createMenuProductRequest(product.getId(), 2L)
        );
        assertThatThrownBy(() -> menuService.create(expected))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 합니다.");
    }

    @DisplayName("메뉴는 특정 메뉴 그룹에 속해야 한다.")
    @NullSource
    @ParameterizedTest
    void create(final UUID menuGroupId) {
        final TobeMenuCreateRequest expected = createMenuRequest(
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
        final TobeMenuCreateRequest expected = createMenuRequest(
                name, 19_000L, menuGroupId, true, createMenuProductRequest(product.getId(), 2L)
        );
        assertThatThrownBy(() -> menuService.create(expected))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("메뉴의 이름은 비어있거나 비속어가 포함될 수 없습니다.");
    }

    @DisplayName("메뉴의 가격을 변경할 수 있다.")
    @Test
    void changePrice() {
        final UUID menuId = menuRepository.save(menu(19_000L, menuProduct(product, 2L))).getId();
        final BigDecimal expected = BigDecimal.valueOf(16_000L);
        final TobeMenu actual = menuService.changePrice(menuId, expected);
        assertThat(actual.getPrice()).isEqualTo(new MenuPrice(expected));
    }

    @DisplayName("메뉴의 가격이 올바르지 않으면 변경할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void changePrice(final BigDecimal price) {
        final UUID menuId = menuRepository.save(menu(19_000L, menuProduct(product, 2L))).getId();
        assertThatThrownBy(() -> menuService.changePrice(menuId, price))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("메뉴의 가격은 0원 이상이어야 합니다.");
    }

    @DisplayName("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.")
    @Test
    void changePriceToExpensive() {
        final UUID menuId = menuRepository.save(menu(19_000L, menuProduct(product, 2L))).getId();
        final BigDecimal expected = BigDecimal.valueOf(33_000L);

        assertThatThrownBy(() -> menuService.changePrice(menuId, expected))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 합니다.");
    }

    @DisplayName("메뉴를 노출할 수 있다.")
    @Test
    void display() {
        final UUID menuId = menuRepository.save(menu(19_000L, false, menuProduct(product, 2L))).getId();
        final TobeMenu actual = menuService.display(menuId);
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
        final TobeMenu actual = menuService.hide(menuId);
        assertThat(actual.isDisplayed()).isFalse();
    }

    @DisplayName("메뉴의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        menuRepository.save(menu(19_000L, true, menuProduct(product, 2L)));
        final List<TobeMenu> actual = menuService.findAll();
        assertThat(actual).hasSize(1);
    }

    private TobeMenuCreateRequest createMenuRequest(
            final String name,
            final long price,
            final UUID menuGroupId,
            final boolean displayed,
            final TobeMenuProductRequest... menuProducts
    ) {
        return createMenuRequest(name, BigDecimal.valueOf(price), menuGroupId, displayed, menuProducts);
    }

    private TobeMenuCreateRequest createMenuRequest(
            final String name,
            final BigDecimal price,
            final UUID menuGroupId,
            final boolean displayed,
            final TobeMenuProductRequest... menuProducts
    ) {
        return createMenuRequest(name, price, menuGroupId, displayed, Arrays.asList(menuProducts));
    }

    private TobeMenuCreateRequest createMenuRequest(
            final String name,
            final long price,
            final UUID menuGroupId,
            final boolean displayed,
            final List<TobeMenuProductRequest> menuProducts
    ) {
        return createMenuRequest(name, BigDecimal.valueOf(price), menuGroupId, displayed, menuProducts);
    }

    private TobeMenuCreateRequest createMenuRequest(
            final String name,
            final BigDecimal price,
            final UUID menuGroupId,
            final boolean displayed,
            final List<TobeMenuProductRequest> menuProducts
    ) {
        return new TobeMenuCreateRequest(name, price, null, displayed, menuProducts, menuGroupId);
    }

    private static TobeMenuProductRequest createMenuProductRequest(final UUID productId, final long quantity) {
        return new TobeMenuProductRequest(productId, quantity);
    }

    private TobeMenu changePriceRequest(final long price) {
        return changePriceRequest(BigDecimal.valueOf(price));
    }

    private TobeMenu changePriceRequest(final BigDecimal price) {
        return new TobeMenu(new MenuPrice(price));
    }
}
