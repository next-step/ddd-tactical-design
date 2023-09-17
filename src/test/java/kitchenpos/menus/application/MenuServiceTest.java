package kitchenpos.menus.application;

import kitchenpos.common.domain.Purgomalum;
import kitchenpos.common.exception.KitchenPosException;
import kitchenpos.common.exception.KitchenPosExceptionType;
import kitchenpos.common.values.Price;
import kitchenpos.menus.domain.Menu;
import kitchenpos.common.infra.FakePurgomalum;
import kitchenpos.menus.dto.ChangePriceMenuRequest;
import kitchenpos.menus.dto.CreateMenuProductRequest;
import kitchenpos.menus.dto.CreateMenuRequest;
import kitchenpos.menus.dto.MenuDto;
import kitchenpos.menus.infra.InMemoryMenuRepository;
import kitchenpos.menus.domain.MenuGroupRepository;
import kitchenpos.menus.infra.InMemoryMenuGroupRepository;
import kitchenpos.menus.tobe.infra.ToBeInMemoryMenuRepository;
import kitchenpos.products.application.ProductValidator;
import kitchenpos.products.infra.InMemoryProductRepository;
import kitchenpos.products.domain.Product;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.util.KitchenPostExceptionAssertionUtils;
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
import static kitchenpos.common.exception.KitchenPosExceptionType.BAD_REQUEST;
import static kitchenpos.common.exception.KitchenPosExceptionType.NOT_FOUND;
import static kitchenpos.util.KitchenPostExceptionAssertionUtils.assertThrows;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MenuServiceTest {
    private ProductValidator productValidator;
    private MenuValidator menuValidator;
    private MenuProductService menuProductService;
    private InMemoryMenuRepository menuRepository;
    private ToBeInMemoryMenuRepository toBeMenuRepository;
    private MenuGroupRepository menuGroupRepository;
    private ProductRepository productRepository;
    private Purgomalum purgomalum;
    private MenuService menuService;
    private UUID menuGroupId;
    private Product product;

    @BeforeEach
    void setUp() {
        menuRepository = new InMemoryMenuRepository();
        toBeMenuRepository = new ToBeInMemoryMenuRepository();
        menuGroupRepository = new InMemoryMenuGroupRepository();
        productRepository = new InMemoryProductRepository();
        purgomalum = FakePurgomalum.create();
        productValidator = new ProductValidator(productRepository);
        menuValidator = new MenuValidator(productRepository);
        menuProductService = new MenuProductService(productValidator);
        menuService = new MenuService(
                menuValidator, productValidator, menuProductService, menuRepository,
                toBeMenuRepository, menuGroupRepository, productRepository, purgomalum
        );
        menuGroupId = menuGroupRepository.save(menuGroup()).getId();
        product = productRepository.save(product("후라이드", 16_000L));
    }

    @DisplayName("1개 이상의 등록된 상품으로 메뉴를 등록할 수 있다.")
    @Test
    void create() {
        final CreateMenuRequest expected = createMenuRequest(
            "후라이드+후라이드", 19_000L, menuGroupId, true, createToBeMenuProductRequest(product.getId(), 2L)
        );
        final MenuDto actual = menuService.create(expected);
        assertThat(actual).isNotNull();
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertTrue(actual.getName().equalValue(expected.getName())),
            () -> assertTrue(actual.getPrice().equalValue(expected.getPrice())),
            () -> assertThat(actual.getMenuGroup().getId()).isEqualTo(expected.getMenuGroupId()),
            () -> assertThat(actual.isDisplayed()).isEqualTo(expected.isDisplayed()),
            () -> assertThat(actual.getMenuProducts()).hasSize(1)
        );
    }

    @DisplayName("상품이 없으면 등록할 수 없다.")
    @MethodSource("menuProducts")
    @ParameterizedTest
    void create(final List<CreateMenuProductRequest> menuProducts) {
        final CreateMenuRequest expected = createMenuRequest("후라이드+후라이드", 19_000L, menuGroupId, true, menuProducts);
        assertThatThrownBy(() -> menuService.create(expected))
            .isInstanceOf(KitchenPosException.class);
    }

    private static List<Arguments> menuProducts() {
        return Arrays.asList(
            null,
            Arguments.of(Collections.emptyList()),
            Arguments.of(Arrays.asList(createToBeMenuProductRequest(INVALID_ID, 2L)))
        );
    }

    @DisplayName("메뉴에 속한 상품의 수량은 0개 이상이어야 한다.")
    @Test
    void createNegativeQuantity() {
        final CreateMenuRequest expected = createMenuRequest(
            "후라이드+후라이드", 19_000L, menuGroupId, true, createToBeMenuProductRequest(product.getId(), -1L)
        );
        assertThrows(BAD_REQUEST, () -> menuService.create(expected));
    }

    @DisplayName("메뉴의 가격이 올바르지 않으면 등록할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void create(final BigDecimal price) {
        final CreateMenuRequest expected = createMenuRequest(
            "후라이드+후라이드", price, menuGroupId, true, createToBeMenuProductRequest(product.getId(), 2L)
        );
        assertThrows(BAD_REQUEST, () -> menuService.create(expected));
    }

    @DisplayName("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.")
    @Test
    void createExpensiveMenu() {
        final CreateMenuRequest expected = createMenuRequest(
            "후라이드+후라이드", 33_000L, menuGroupId, true, createToBeMenuProductRequest(product.getId(), 2L)
        );
        assertThrows(BAD_REQUEST, () -> menuService.create(expected));
    }

    @DisplayName("메뉴는 특정 메뉴 그룹에 속해야 한다.")
    @NullSource
    @ParameterizedTest
    void create(final UUID menuGroupId) {
        final CreateMenuRequest expected = createMenuRequest(
            "후라이드+후라이드", 19_000L, menuGroupId, true, createToBeMenuProductRequest(product.getId(), 2L)
        );
        assertThrows(NOT_FOUND, () -> menuService.create(expected));
    }

    @DisplayName("메뉴의 이름이 올바르지 않으면 등록할 수 없다.")
    @ValueSource(strings = {"비속어", "욕설이 포함된 이름"})
    @NullSource
    @ParameterizedTest
    void create(final String name) {
        final CreateMenuRequest expected = createMenuRequest(
            name, 19_000L, menuGroupId, true, createToBeMenuProductRequest(product.getId(), 2L)
        );

        assertThrows(BAD_REQUEST, () -> menuService.create(expected));
    }

    @DisplayName("메뉴의 가격을 변경할 수 있다.")
    @Test
    void changePrice() {
        final UUID menuId = toBeMenuRepository.save(menu(19_000L, menuProduct(product, 2L))).getId();
        final ChangePriceMenuRequest request = changePriceRequest(16_000L);
        final MenuDto actual = menuService.changePrice(menuId, request);
        assertTrue(actual.getPrice().equalValue(request.getPrice()));
    }

    @DisplayName("메뉴의 가격이 올바르지 않으면 변경할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void changePrice(final BigDecimal price) {
        final UUID menuId = toBeMenuRepository.save(menu(19_000L, menuProduct(product, 2L))).getId();
        final ChangePriceMenuRequest request = changePriceRequest(price);
        assertThrows(BAD_REQUEST, () -> menuService.changePrice(menuId, request));
    }

    @DisplayName("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.")
    @Test
    void changePriceToExpensive() {
        final UUID menuId = toBeMenuRepository.save(menu(19_000L, menuProduct(product, 2L))).getId();
        final ChangePriceMenuRequest request = changePriceRequest(33_000L);
        assertThrows(BAD_REQUEST, () -> menuService.changePrice(menuId, request));
    }

    @DisplayName("메뉴를 노출할 수 있다.")
    @Test
    void display() {
        final UUID menuId = toBeMenuRepository.save(menu(19_000L, false, menuProduct(product, 2L))).getId();
        final MenuDto actual = menuService.display(menuId);
        assertThat(actual.isDisplayed()).isTrue();
    }

    @DisplayName("메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 높을 경우 메뉴를 노출할 수 없다.")
    @Test
    void displayExpensiveMenu() {
        final UUID menuId = toBeMenuRepository.save(menu(33_000L, false, menuProduct(product, 2L))).getId();
        assertThrows(BAD_REQUEST, () -> menuService.display(menuId));
    }

    @DisplayName("메뉴를 숨길 수 있다.")
    @Test
    void hide() {
        final UUID menuId = toBeMenuRepository.save(menu(19_000L, true, menuProduct(product, 2L))).getId();
        final MenuDto actual = menuService.hide(menuId);
        assertThat(actual.isDisplayed()).isFalse();
    }

    @DisplayName("메뉴의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        toBeMenuRepository.save(menu(19_000L, true, menuProduct(product, 2L)));
        final List<MenuDto> actual = menuService.findAll();
        assertThat(actual).hasSize(1);
    }

    private CreateMenuRequest createMenuRequest(
        final String name,
        final long price,
        final UUID menuGroupId,
        final boolean displayed,
        final CreateMenuProductRequest... menuProducts
    ) {
        return createMenuRequest(name, BigDecimal.valueOf(price), menuGroupId, displayed, menuProducts);
    }

    private CreateMenuRequest createMenuRequest(
        final String name,
        final BigDecimal price,
        final UUID menuGroupId,
        final boolean displayed,
        final CreateMenuProductRequest... menuProducts
    ) {
        return createMenuRequest(name, price, menuGroupId, displayed, Arrays.asList(menuProducts));
    }

    private CreateMenuRequest createMenuRequest(
        final String name,
        final long price,
        final UUID menuGroupId,
        final boolean displayed,
        final List<CreateMenuProductRequest> menuProducts
    ) {
        return createMenuRequest(name, BigDecimal.valueOf(price), menuGroupId, displayed, menuProducts);
    }

    private CreateMenuRequest createMenuRequest(
        final String name,
        final BigDecimal price,
        final UUID menuGroupId,
        final boolean displayed,
        final List<CreateMenuProductRequest> menuProducts
    ) {
        return new CreateMenuRequest(
                name, price, menuGroupId, displayed, menuProducts
        );
    }

    private static CreateMenuProductRequest createToBeMenuProductRequest(final UUID productId, final long quantity) {
        return new CreateMenuProductRequest(productId, quantity);
    }

    private ChangePriceMenuRequest changePriceRequest(final long price) {
        return changePriceRequest(BigDecimal.valueOf(price));
    }

    private ChangePriceMenuRequest changePriceRequest(final BigDecimal price) {
        return new ChangePriceMenuRequest(price);
    }
}
