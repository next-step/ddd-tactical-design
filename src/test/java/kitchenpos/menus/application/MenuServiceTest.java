package kitchenpos.menus.application;

import kitchenpos.common.domain.DisplayNameChecker;
import kitchenpos.menus.application.dto.*;
import kitchenpos.menus.tobe.domain.MenuGroupRepository;
import kitchenpos.menus.tobe.domain.MenuRepository;
import kitchenpos.menus.application.dto.MenuProductCreateRequest;
import kitchenpos.menus.tobe.domain.NewProduct;
import kitchenpos.products.application.FakeDisplayNameChecker;
import kitchenpos.products.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.*;

import static kitchenpos.NewFixtures.*;
import static kitchenpos.menus.exception.MenuExceptionMessage.MENU_PRICE_MORE_PRODUCTS_SUM;
import static kitchenpos.menus.exception.MenuProductExceptionMessage.NOT_EQUAL_MENU_PRODUCT_SIZE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class MenuServiceTest {
    private MenuRepository menuRepository;
    private MenuGroupRepository menuGroupRepository;
    private InMemoryProductRepository productRepository;
    private DisplayNameChecker displayNameChecker;
    private MenuService menuService;
    private UUID menuGroupId;
    private NewProduct product;

    @BeforeEach
    void setUp() {
        menuRepository = new NewInMemoryMenuRepository();
        menuGroupRepository = new NewInMemoryMenuGroupRepository();
        displayNameChecker = new FakeDisplayNameChecker();
        productRepository = new InMemoryProductRepository();
        menuService = new MenuService(menuRepository, menuGroupRepository, displayNameChecker, productRepository);
        menuGroupId = menuGroupRepository.save(tobeMenuGroup("두마리메뉴")).getId();
        product = productRepository.save(newProduct(19_000L));
    }

    @DisplayName("1개 이상의 등록된 상품으로 메뉴를 등록할 수 있다.")
    @Test
    void create() {
        final MenuCreateRequest expected = createMenuRequest(
                "후라이드+후라이드", 19_000L, menuGroupId, true, createMenuProductRequest(product.getId(), 2L)
        );
        final MenuInfoResponse actual = menuService.create(expected);
        assertThat(actual).isNotNull();
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
                () -> assertThat(actual.getPrice()).isEqualTo(expected.getPrice()),
                () -> assertThat(actual.getMenuGroupId()).isEqualTo(expected.getMenuGroupId()),
                () -> assertThat(actual.isDisplayed()).isEqualTo(expected.isDisplayed()),
                () -> assertThat(actual.getMenuProductCreateResponseList()).hasSize(1)
        );
    }

    @DisplayName("상품이 없으면 등록할 수 없다.")
    @MethodSource("menuProductCreateRequests")
    @ParameterizedTest
    void create(final List<MenuProductCreateRequest> menuProducts) {
        final MenuCreateRequest expected = createMenuRequest("후라이드+후라이드", 19_000L, menuGroupId, true, menuProducts);
        assertThatThrownBy(() -> menuService.create(expected))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(NOT_EQUAL_MENU_PRODUCT_SIZE);
    }

    private static List<Arguments> menuProductCreateRequests() {
        return Arrays.asList(
                null,
                Arguments.of(Collections.emptyList()),
                Arguments.of(Arrays.asList(createMenuProductRequest(INVALID_ID, 2L)))
        );
    }

    @DisplayName("메뉴에 속한 상품의 수량은 0개 이상이어야 한다.")
    @Test
    void createNegativeQuantity() {
        final MenuCreateRequest expected = createMenuRequest(
                "후라이드+후라이드", 19_000L, menuGroupId, true, createMenuProductRequest(product.getId(), -1L)
        );
        assertThatThrownBy(() -> menuService.create(expected))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴의 가격이 올바르지 않으면 등록할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void create(final Long price) {
        final MenuCreateRequest expected = createMenuRequest(
                "후라이드+후라이드", price, menuGroupId, true, createMenuProductRequest(product.getId(), 2L)
        );
        assertThatThrownBy(() -> menuService.create(expected))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴의 가격은 메뉴에 속한 상품 금액의 합보다 작거나 같아야 한다.")
    @Test
    void createExpensiveMenu() {
        final MenuCreateRequest expected = createMenuRequest(
                "후라이드+후라이드", 33_000L, menuGroupId, true, createMenuProductRequest(product.getId(), 1L)
        );
        assertThatThrownBy(() -> menuService.create(expected))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(MENU_PRICE_MORE_PRODUCTS_SUM);
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

    @Nested
    @DisplayName("가격 변경 테스트")
    class changePrice {
        @DisplayName("메뉴의 가격을 변경할 수 있다.")
        @Test
        void changePrice() {
            final UUID menuId = menuRepository.save(menu(19_000L, menuProduct(product, 2L))).getId();
            final MenuChangePriceRequest expected = changePriceRequest(16_000L);
            final MenuChangePriceResponse actual = menuService.changePrice(menuId, expected);
            assertThat(actual.getChangedPrice()).isEqualTo(expected.getPrice());
        }

        @DisplayName("메뉴의 가격이 올바르지 않으면 변경할 수 없다.")
        @ValueSource(strings = "-1000")
        @NullSource
        @ParameterizedTest
        void changePrice(final BigDecimal price) {
            final UUID menuId = menuRepository.save(menu(19_000L, menuProduct(product, 2L))).getId();
            final MenuChangePriceRequest expected = changePriceRequest(price);
            assertThatThrownBy(() -> menuService.changePrice(menuId, expected))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.")
        @Test
        void changePriceToExpensive() {
            final UUID menuId = menuRepository.save(menu(19_000L, menuProduct(product, 2L))).getId();
            final MenuChangePriceRequest expected = changePriceRequest(50_000L);
            assertThatThrownBy(() -> menuService.changePrice(menuId, expected))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("메뉴 노출/비노출 테스트")
    class display {
        @DisplayName("메뉴를 노출할 수 있다.")
        @Test
        void display() {
            final UUID menuId = menuRepository.save(menu(19_000L, false, menuProduct(product, 2L))).getId();
            final MenuDisplayResponse actual = menuService.display(menuId);
            assertThat(actual.isDisplayed()).isTrue();
        }

        @DisplayName("메뉴를 숨길 수 있다.")
        @Test
        void hide() {
            final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct(product, 2L))).getId();
            final MenuDisplayResponse actual = menuService.hide(menuId);
            assertThat(actual.isDisplayed()).isFalse();
        }
    }


    @DisplayName("메뉴의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        menuRepository.save(menu(19_000L, true, menuProduct(product, 2L)));
        final List<MenuInfoResponse> actual = menuService.findAll();
        assertThat(actual).hasSize(1);
    }

    private MenuCreateRequest createMenuRequest(
            final String name,
            final Long price,
            final UUID menuGroupId,
            final boolean displayed,
            final List<MenuProductCreateRequest> menuProductCreateRequests
    ) {
        return MenuCreateRequest.create(BigDecimal.valueOf(price), menuGroupId, menuProductCreateRequests, name, displayed);
    }

    private MenuCreateRequest createMenuRequest(
            final String name,
            final Long price,
            final UUID menuGroupId,
            final boolean displayed,
            final MenuProductCreateRequest... menuProductCreateRequests
    ) {
        if (price == null) {
            return MenuCreateRequest.create(null, menuGroupId, List.of(menuProductCreateRequests), name, displayed);
        }
        return MenuCreateRequest.create(BigDecimal.valueOf(price), menuGroupId, List.of(menuProductCreateRequests), name, displayed
        );
    }

    private static MenuProductCreateRequest createMenuProductRequest(final UUID productId, final long quantity) {
        return MenuProductCreateRequest.create(productId, quantity);
    }

    private MenuChangePriceRequest changePriceRequest(final long price) {
        return changePriceRequest(BigDecimal.valueOf(price));
    }

    private MenuChangePriceRequest changePriceRequest(final BigDecimal price) {
        return new MenuChangePriceRequest(price);
    }
}
