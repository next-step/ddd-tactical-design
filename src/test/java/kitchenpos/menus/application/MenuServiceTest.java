package kitchenpos.menus.application;

import static kitchenpos.Fixtures.INVALID_ID;
import static kitchenpos.menus.MenuFixtures.menu;
import static kitchenpos.menus.MenuFixtures.menuGroup;
import static kitchenpos.products.ProductFixtures.product;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.math.BigDecimal;
import java.util.*;
import kitchenpos.menus.domain.*;
import kitchenpos.menus.ui.request.MenuCreateRequest;
import kitchenpos.menus.ui.request.MenuPriceChangeRequest;
import kitchenpos.menus.ui.request.MenuProductCreateRequest;
import kitchenpos.menus.ui.response.MenuResponse;
import kitchenpos.products.domain.InMemoryProductRepository;
import kitchenpos.products.domain.Product;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.profanity.infra.FakeProfanityCheckClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class MenuServiceTest {

    private MenuRepository menuRepository;
    private MenuService menuService;
    private UUID menuGroupId;
    private Product product;

    @BeforeEach
    void setUp() {
        ProductRepository productRepository = new InMemoryProductRepository();
        product = productRepository.save(product("후라이드", 16_000L));
        MenuGroupRepository menuGroupRepository = new InMemoryMenuGroupRepository();
        menuGroupId = menuGroupRepository.save(menuGroup()).getId();
        menuRepository = new InMemoryMenuRepository();
        menuService = new MenuService(
            menuRepository,
            menuGroupRepository,
            new FakeProfanityCheckClient(),
            new FakeProductPriceReader(productRepository)
        );
    }

    @DisplayName("1개 이상의 등록된 상품으로 메뉴를 등록할 수 있다.")
    @Test
    void create() {
        MenuCreateRequest request = createMenuRequest(
            "후라이드+후라이드",
            19_000L,
            menuGroupId,
            createMenuProductRequest(product.getId(), 2L)
        );
        MenuResponse response = menuService.create(request);
        assertThat(response).isNotNull();
        assertAll(
            () -> assertThat(response.getId()).isNotNull(),
            () -> assertThat(response.getName()).isEqualTo(request.getName()),
            () -> assertThat(response.getPrice()).isEqualTo(request.getPrice()),
            () -> assertThat(response.getMenuGroupId()).isEqualTo(request.getMenuGroupId()),
            () -> assertThat(response.isDisplayed()).isTrue(),
            () -> assertThat(response.getMenuProducts()).hasSize(1)
        );
    }

    @DisplayName("상품이 0개면 등록할 수 없다.")
    @Test
    void createWithNoProductsException() {
        MenuCreateRequest request = createMenuRequest(
            "후라이드+후라이드",
            19_000L,
            menuGroupId,
            new ArrayList<>()
        );
        assertThatThrownBy(() -> menuService.create(request))
            .isExactlyInstanceOf(IllegalArgumentException.class)
            .hasMessage("메뉴의 상품은 1개 이상이어야 합니다.");
    }

    @DisplayName("ID에 해당하는 상품이 없으면 등록할 수 없다.")
    @Test
    void productNotFoundException() {
        MenuCreateRequest request = createMenuRequest(
            "후라이드+후라이드",
            19_000L,
            menuGroupId,
            createMenuProductRequest(INVALID_ID, 2L)
        );
        assertThatThrownBy(() -> menuService.create(request))
            .isExactlyInstanceOf(NoSuchElementException.class)
            .hasMessage("ID 에 해당하는 상품을 찾을 수 없습니다.");
    }

    @DisplayName("메뉴에 속한 상품의 수량은 0개 이상이어야 한다.")
    @Test
    void createNegativeQuantity() {
        final MenuCreateRequest request = createMenuRequest(
            "후라이드+후라이드", 19_000L, menuGroupId, createMenuProductRequest(product.getId(), -1L)
        );
        assertThatThrownBy(() -> menuService.create(request))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴의 가격이 올바르지 않으면 등록할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void create(final BigDecimal price) {
        final MenuCreateRequest request = createMenuRequest(
            "후라이드+후라이드", price, menuGroupId, createMenuProductRequest(product.getId(), 2L)
        );
        assertThatThrownBy(() -> menuService.create(request))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.")
    @Test
    void createExpensiveMenu() {
        final MenuCreateRequest request = createMenuRequest(
            "후라이드+후라이드", 33_000L, menuGroupId, createMenuProductRequest(product.getId(), 2L)
        );
        assertThatThrownBy(() -> menuService.create(request))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴는 특정 메뉴 그룹에 속해야 한다.")
    @NullSource
    @ParameterizedTest
    void create(final UUID menuGroupId) {
        final MenuCreateRequest request = createMenuRequest(
            "후라이드+후라이드", 19_000L, menuGroupId, createMenuProductRequest(product.getId(), 2L)
        );
        assertThatThrownBy(() -> menuService.create(request))
            .isInstanceOf(NoSuchElementException.class);
    }

    @DisplayName("메뉴의 이름이 올바르지 않으면 등록할 수 없다.")
    @ValueSource(strings = {"비속어", "욕설이 포함된 이름"})
    @NullSource
    @ParameterizedTest
    void create(final String name) {
        final MenuCreateRequest request = createMenuRequest(
            name, 19_000L, menuGroupId, createMenuProductRequest(product.getId(), 2L)
        );
        assertThatThrownBy(() -> menuService.create(request))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴의 가격을 변경할 수 있다.")
    @Test
    void changePrice() {
        final UUID menuId = menuRepository.save(menu(19_000L)).getId();
        final MenuPriceChangeRequest request = changePriceRequest();
        final MenuResponse response = menuService.changePrice(menuId, request);
        assertThat(response.getPrice()).isEqualTo(request.getPrice());
    }

    @DisplayName("메뉴의 가격이 올바르지 않으면 변경할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void changePrice(final BigDecimal price) {
        final UUID menuId = menuRepository.save(menu(19_000L)).getId();
        final MenuPriceChangeRequest request = changePriceRequest(price);
        assertThatThrownBy(() -> menuService.changePrice(menuId, request))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴를 노출할 수 있다.")
    @Test
    void display() {
        final UUID menuId = menuRepository.save(menu(19_000L)).getId();
        final MenuResponse response = menuService.display(menuId);
        assertThat(response.isDisplayed()).isTrue();
    }

    @DisplayName("메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 높을 경우 메뉴를 노출할 수 없다.")
    @Test
    void displayExpensiveMenu() {
        Menu menu = menuRepository.save(menu());
        menu.changePrice(new MenuPrice(BigDecimal.valueOf(33_000L)));
        assertThatThrownBy(() -> menuService.display(menu.getId()))
            .isExactlyInstanceOf(IllegalArgumentException.class)
            .hasMessage("메뉴의 가격이 상품보다 높아 전시상태를 변경할 수 없습니다.");
    }

    @DisplayName("메뉴를 숨길 수 있다.")
    @Test
    void hide() {
        final UUID menuId = menuRepository.save(menu(19_000L)).getId();
        final MenuResponse response = menuService.hide(menuId);
        assertThat(response.isDisplayed()).isFalse();
    }

    @DisplayName("메뉴의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        menuRepository.save(menu(19_000L));
        final List<MenuResponse> responses = menuService.findAll();
        assertThat(responses).hasSize(1);
    }

    private MenuCreateRequest createMenuRequest(
        final String name,
        final long price,
        final UUID menuGroupId,
        final MenuProductCreateRequest... menuProductCreateRequests
    ) {
        return createMenuRequest(
            name,
            BigDecimal.valueOf(price),
            menuGroupId,
            menuProductCreateRequests
        );
    }

    private MenuCreateRequest createMenuRequest(
        final String name,
        final BigDecimal price,
        final UUID menuGroupId,
        final MenuProductCreateRequest... menuProductCreateRequests
    ) {
        return createMenuRequest(
            name,
            price,
            menuGroupId,
            Arrays.asList(menuProductCreateRequests)
        );
    }

    private MenuCreateRequest createMenuRequest(
        final String name,
        final long price,
        final UUID menuGroupId,
        final List<MenuProductCreateRequest> menuProductCreateRequests
    ) {
        return createMenuRequest(
            name,
            BigDecimal.valueOf(price),
            menuGroupId,
            menuProductCreateRequests
        );
    }

    private MenuCreateRequest createMenuRequest(
        final String name,
        final BigDecimal price,
        final UUID menuGroupId,
        final List<MenuProductCreateRequest> menuProductCreateRequests
    ) {
        return new MenuCreateRequest(
            name,
            price,
            menuGroupId,
            menuProductCreateRequests
        );
    }

    private static MenuProductCreateRequest createMenuProductRequest(
        final UUID productId,
        final long quantity
    ) {
        return new MenuProductCreateRequest(
            productId,
            quantity
        );
    }

    private MenuPriceChangeRequest changePriceRequest() {
        return changePriceRequest(BigDecimal.valueOf(16000));
    }

    private MenuPriceChangeRequest changePriceRequest(final BigDecimal price) {
        return new MenuPriceChangeRequest(price);
    }
}
