package kitchenpos.menus.application;

import kitchenpos.menus.tobe.domain.entity.IncludedProduct;
import kitchenpos.menus.tobe.domain.entity.Menu;
import kitchenpos.menus.tobe.domain.repository.IncludedProductRepository;
import kitchenpos.menus.tobe.domain.repository.MenuGroupRepository;
import kitchenpos.menus.tobe.domain.repository.MenuRepository;
import kitchenpos.menus.ui.dto.ChangePriceRequest;
import kitchenpos.menus.ui.dto.CreateMenuRequest;
import kitchenpos.products.application.FakePurgomalumValidator;
import kitchenpos.common.domain.infra.PurgomalumValidator;
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
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class MenuServiceTest {
    private MenuGroupRepository menuGroupRepository;
    private IncludedProductRepository includedProductRepository;
    private MenuRepository menuRepository;
    private PurgomalumValidator purgomalumValidator;
    private CreateMenuService createMenuService;
    private ChangePriceService changePriceService;
    private DisplayMenuService displayMenuService;
    private HideMenuService hideMenuService;
    private FindAllMenuService findAllMenuService;
    private UUID menuGroupId;
    private IncludedProduct includedProduct;


    @BeforeEach
    void setUp() {
        InMemoryIncludedProductRepository inMemoryIncludedProductRepository = new InMemoryIncludedProductRepository();
        includedProduct = inMemoryIncludedProductRepository.save(includedProduct(16_000L));
        includedProductRepository = inMemoryIncludedProductRepository;

        menuGroupRepository = new InMemoryMenuGroupRepository();
        menuGroupId = menuGroupRepository.save(menuGroup()).getId();

        menuRepository = new InMemoryMenuRepository();

        purgomalumValidator = new FakePurgomalumValidator();
        createMenuService = new CreateMenuService(menuGroupRepository, purgomalumValidator, includedProductRepository, menuRepository);
        changePriceService = new ChangePriceService(menuRepository);
        displayMenuService = new DisplayMenuService(menuRepository);
        hideMenuService = new HideMenuService(menuRepository);
        findAllMenuService = new FindAllMenuService(menuRepository);
    }

    @DisplayName("1개 이상의 등록된 상품으로 메뉴를 등록할 수 있다.")
    @Test
    void create() {
        final CreateMenuRequest expected = createMenuRequest(
            "후라이드+후라이드", 19_000L, menuGroupId, true, createMenuProductRequest(includedProduct.getId(), 2L)
        );
        final kitchenpos.menus.tobe.domain.entity.Menu actual = createMenuService.create(expected);
        assertThat(actual).isNotNull();
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getName().getName()).isEqualTo(expected.name),
            () -> assertThat(actual.getPrice().getPrice()).isEqualTo(expected.price),
            () -> assertThat(actual.getMenuGroup().getId()).isEqualTo(expected.menuGroupId),
            () -> assertThat(actual.isDisplayed()).isEqualTo(expected.isDisplayed),
            () -> assertThat(actual.getMenuProducts()).hasSize(1)
        );
    }

    @DisplayName("상품이 없으면 등록할 수 없다.")
    @MethodSource("menuProducts")
    @ParameterizedTest
    void create(final List<CreateMenuRequest.CreateMenuProductRequest> menuProductRequests) {
        final CreateMenuRequest expected = createMenuRequest("후라이드+후라이드", 19_000L, menuGroupId, true, menuProductRequests);
        assertThatThrownBy(() -> createMenuService.create(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    private static List<Arguments> menuProducts() {
        return List.of(
                Arguments.of(List.of(createMenuProductRequest(INVALID_ID, 2L)))
        );
    }

    @DisplayName("메뉴에 속한 상품의 수량은 0개 이상이어야 한다.")
    @Test
    void createNegativeQuantity() {
        final CreateMenuRequest expected = createMenuRequest(
            "후라이드+후라이드", 19_000L, menuGroupId, true, createMenuProductRequest(includedProduct.getId(), -1L)
        );
        assertThatThrownBy(() -> createMenuService.create(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴의 가격이 올바르지 않으면 등록할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void create(final BigDecimal price) {
        final CreateMenuRequest expected = createMenuRequest(
            "후라이드+후라이드", price, menuGroupId, true, createMenuProductRequest(includedProduct.getId(), 2L)
        );
        assertThatThrownBy(() -> createMenuService.create(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.")
    @Test
    void createExpensiveMenu() {
        final CreateMenuRequest expected = createMenuRequest(
            "후라이드+후라이드", 33_000L, menuGroupId, true, createMenuProductRequest(includedProduct.getId(), 2L)
        );
        assertThatThrownBy(() -> createMenuService.create(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴는 특정 메뉴 그룹에 속해야 한다.")
    @NullSource
    @ParameterizedTest
    void create(final UUID menuGroupId) {
        final CreateMenuRequest expected = createMenuRequest(
            "후라이드+후라이드", 19_000L, menuGroupId, true, createMenuProductRequest(includedProduct.getId(), 2L)
        );
        assertThatThrownBy(() -> createMenuService.create(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴의 이름이 올바르지 않으면 등록할 수 없다.")
    @ValueSource(strings = {"비속어", "욕설이 포함된 이름"})
    @NullSource
    @ParameterizedTest
    void create(final String name) {
        final CreateMenuRequest expected = createMenuRequest(
            name, 19_000L, menuGroupId, true, createMenuProductRequest(includedProduct.getId(), 2L)
        );
        assertThatThrownBy(() -> createMenuService.create(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴의 가격을 변경할 수 있다.")
    @Test
    void changePrice() {
        final UUID menuId = menuRepository.save(menu(19_000L, menuProduct(includedProduct, 2L))).getId();
        final ChangePriceRequest expected = changePriceRequest(16_000L);
        final Menu actual = changePriceService.change(menuId, expected);
        assertThat(actual.getPrice().getPrice()).isEqualTo(expected.price);
    }

    @DisplayName("메뉴의 가격이 올바르지 않으면 변경할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void changePrice(final BigDecimal price) {
        final UUID menuId = menuRepository.save(menu(19_000L, menuProduct(includedProduct, 2L))).getId();
        final ChangePriceRequest expected = changePriceRequest(price);
        assertThatThrownBy(() -> changePriceService.change(menuId, expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.")
    @Test
    void changePriceToExpensive() {
        final UUID menuId = menuRepository.save(menu(19_000L, menuProduct(includedProduct, 2L))).getId();
        final ChangePriceRequest expected = changePriceRequest(33_000L);
        assertThatThrownBy(() -> changePriceService.change(menuId, expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴를 노출할 수 있다.")
    @Test
    void display() {
        final UUID menuId = menuRepository.save(menu(19_000L, false, menuProduct(includedProduct, 2L))).getId();
        final Menu actual = displayMenuService.display(menuId);
        assertThat(actual.isDisplayed()).isTrue();
    }

    @DisplayName("메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 높을 경우 메뉴를 노출할 수 없다.")
    @Test
    void displayExpensiveMenu() {
        final UUID menuId = menuRepository.save(menu(33_000L, false, menuProduct(includedProduct, 2L))).getId();
        assertThatThrownBy(() -> displayMenuService.display(menuId))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("메뉴를 숨길 수 있다.")
    @Test
    void hide() {
        final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct(includedProduct, 2L))).getId();
        final Menu actual = hideMenuService.hide(menuId);
        assertThat(actual.isDisplayed()).isFalse();
    }

    @DisplayName("메뉴의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        menuRepository.save(menu(19_000L, true, menuProduct(includedProduct, 2L)));
        final List<Menu> actual = findAllMenuService.findAll();
        assertThat(actual).hasSize(1);
    }

    private CreateMenuRequest createMenuRequest(
        final String name,
        final long price,
        final UUID menuGroupId,
        final boolean displayed,
        final CreateMenuRequest.CreateMenuProductRequest... menuProductRequests
    ) {
        return createMenuRequest(name, BigDecimal.valueOf(price), menuGroupId, displayed, menuProductRequests);
    }

    private CreateMenuRequest createMenuRequest(
        final String name,
        final BigDecimal price,
        final UUID menuGroupId,
        final boolean displayed,
        final CreateMenuRequest.CreateMenuProductRequest... menuProductRequests
    ) {
        return createMenuRequest(name, price, menuGroupId, displayed, Arrays.asList(menuProductRequests));
    }

    private CreateMenuRequest createMenuRequest(
        final String name,
        final long price,
        final UUID menuGroupId,
        final boolean displayed,
        final List<CreateMenuRequest.CreateMenuProductRequest> menuProductRequests
    ) {
        return createMenuRequest(name, BigDecimal.valueOf(price), menuGroupId, displayed, menuProductRequests);
    }

    private CreateMenuRequest createMenuRequest(
        final String name,
        final BigDecimal price,
        final UUID menuGroupId,
        final boolean displayed,
        final List<CreateMenuRequest.CreateMenuProductRequest> menuProductRequests
    ) {
        CreateMenuRequest request = new CreateMenuRequest();

        request.name = name;
        request.price = price;
        request.menuGroupId = menuGroupId;
        request.isDisplayed = displayed;

        request.createMenuProductRequests = menuProductRequests;

        return request;
    }

    private static CreateMenuRequest.CreateMenuProductRequest createMenuProductRequest(final UUID productId, final long quantity) {
        CreateMenuRequest.CreateMenuProductRequest request = new CreateMenuRequest.CreateMenuProductRequest();
        request.productId = productId;
        request.quantity = quantity;
        return request;
    }

    private ChangePriceRequest changePriceRequest(final long price) {
        return changePriceRequest(BigDecimal.valueOf(price));
    }

    private ChangePriceRequest changePriceRequest(final BigDecimal price) {
        ChangePriceRequest request = new ChangePriceRequest();
        request.price = price;
        return request;
    }
}
