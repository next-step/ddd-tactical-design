package kitchenpos.menus.tobe.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import kitchenpos.menus.tobe.application.menu.TobeMenuService;
import kitchenpos.menus.tobe.application.menugroup.TobeMenuGroupService;
import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.menus.tobe.domain.menu.MenuName;
import kitchenpos.menus.tobe.domain.menu.MenuPrice;
import kitchenpos.menus.tobe.dto.menu.MenuChangeRequest;
import kitchenpos.menus.tobe.dto.menu.MenuCreateRequest;
import kitchenpos.menus.tobe.dto.menu.MenuProductRequest;
import kitchenpos.menus.tobe.dto.menu.MenuResponse;
import kitchenpos.mock.TestUtil;
import kitchenpos.mock.adapter.FakeProductPriceAdapter;
import kitchenpos.mock.client.FakePurgomalumClient;
import kitchenpos.mock.fixture.MenuFixture;
import kitchenpos.mock.persistence.FakeMenuGroupRepository;
import kitchenpos.mock.persistence.FakeMenuRepository;
import kitchenpos.mock.persistence.FakeProductRepository;
import kitchenpos.products.tobe.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TobeMenuValidationServiceImplTest {

    private static final String MENU_NAME = "두마리 치킨";
    private static final String BAD_MENU_NAME = "bad 두마리 치킨";
    private static final boolean MENU_DISPLAYED = true;

    private TobeMenuService menuService;
    private TestUtil testUtil;
    private FakeProductPriceAdapter productPriceAdapter;

    @BeforeEach
    void setUp() {
        FakeMenuRepository menuRepository = new FakeMenuRepository();
        FakeMenuGroupRepository menuGroupRepository = new FakeMenuGroupRepository();
        FakeProductRepository productRepository = new FakeProductRepository();
        TobeMenuGroupService menuGroupService = new TobeMenuGroupService(menuGroupRepository);
        FakePurgomalumClient purgomalumClient = new FakePurgomalumClient();
        productPriceAdapter = new FakeProductPriceAdapter(productRepository);
        this.menuService = new TobeMenuService(menuRepository, menuGroupService, purgomalumClient,
            productPriceAdapter);
        this.testUtil = new TestUtil(menuGroupRepository, productRepository, menuRepository,
            productPriceAdapter);
    }

    @DisplayName("메뉴를 생성할 수 있다.")
    @Test
    void create() {
        // given
        int quantity = 1;
        List<MenuProductRequest> menuProductRequests = testUtil.getMenuProductRequests(quantity);
        MenuCreateRequest request = new MenuCreateRequest(MENU_NAME, TestUtil.PRICE,
            testUtil.getSavedMenuGroupId(), menuProductRequests, true);

        // when
        MenuResponse menuResponse = menuService.create(request);

        // then
        assertAll(
            () -> assertThat(menuResponse.name()).isEqualTo(MENU_NAME),
            () -> assertThat(menuResponse.price()).isEqualTo(TestUtil.PRICE),
            () -> assertThat(menuResponse.displayed()).isEqualTo(MENU_DISPLAYED),
            () -> assertThat(menuResponse.menuGroupId()).isEqualTo(testUtil.getSavedMenuGroupId()),
            () -> assertThat(menuResponse.menuProducts().getFirst().getProductId()).isEqualTo(
                menuProductRequests.getFirst().productId()),
            () -> assertThat(menuResponse.menuProducts().getFirst().getQuantity()).isEqualTo(
                menuProductRequests.getFirst().quantity())
        );
    }

    @DisplayName("마이너스 가격으로 메뉴를 생성 시, 예외가 발생 한다.")
    @Test
    void createPriceException() {
        // given
        int quantity = 1;
        MenuCreateRequest request = new MenuCreateRequest(MENU_NAME, TestUtil.MINUS_PRICE,
            testUtil.getSavedMenuGroupId(), testUtil.getMenuProductRequests(quantity), true);

        // when then
        assertThatThrownBy(() -> menuService.create(request))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품 구성 없이 메뉴를 생성 시, 예외가 발생 한다.")
    @Test
    void createMenuProductsException() {
        // given
        MenuCreateRequest request = MenuFixture.createCreateRequest(testUtil.getSavedMenuGroupId(),
            null);

        // when then
        assertThatThrownBy(() -> menuService.create(request))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품 구성 개수가 0개이면 메뉴 생성 시, 예외가 발생 한다.")
    @Test
    void createMenuProductQuantityException() {
        // given
        int quantity = 0;
        MenuCreateRequest request = new MenuCreateRequest(MENU_NAME, TestUtil.PRICE,
            testUtil.getSavedMenuGroupId(), testUtil.getMenuProductRequests(quantity), true);

        // when then
        assertThatThrownBy(() -> menuService.create(request))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("구성한 상품 가격이 마이너스 가격이면 메뉴 생성 시, 예외가 발생 한다.")
    @Test
    void createMenuProductPriceException() {
        // given
        int quantity = 1;

        String productName = "양념치킨";
        BigDecimal productPrice = BigDecimal.ZERO;
        Product product = testUtil.getSavedProduct(productName, productPrice);

        MenuCreateRequest request = new MenuCreateRequest(MENU_NAME, TestUtil.MINUS_PRICE,
            testUtil.getSavedMenuGroupId(), testUtil.getMenuProductRequests(quantity), true);

        // when then
        assertThatThrownBy(() -> menuService.create(request))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage(MenuPrice.ERROR_MESSAGE_PRICE);
    }

    @DisplayName("비속어 이름으로 메뉴 생성 시, 예외가 발생 한다.")
    @Test
    void createNameException() {
        // given
        int quantity = 2;

        // when then
        assertThatThrownBy(
            () -> MenuFixture.create(BAD_MENU_NAME, TestUtil.PRICE, testUtil.getSavedMenuGroup(),
                testUtil.getMenuProducts(quantity), productPriceAdapter))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage(MenuName.ERROR_MESSAGE_PROFANITY);
    }

    @DisplayName("메뉴 가격을 수정할 수 있다.")
    @Test
    void changePrice() {
        // given
        Menu menu = MenuFixture.create(MENU_NAME, TestUtil.PRICE,
            testUtil.getSavedMenuGroup(),
            testUtil.getMenuProducts(1),
            productPriceAdapter);
        MenuResponse savedMenuResponse = menuService.create(MenuFixture.toRequest(menu));

        UUID id = savedMenuResponse.id();
        BigDecimal newPrice = BigDecimal.valueOf(10_000);
        MenuChangeRequest changeRequest = new MenuChangeRequest(newPrice);

        // when
        MenuResponse updatedMenuResponse = menuService.changePrice(id, changeRequest);

        // then
        assertThat(updatedMenuResponse.price()).isEqualTo(newPrice);
    }

    @DisplayName("마이너스 가격으로 메뉴 가격 수정 시, 예외가 발생한다.")
    @Test
    void changePriceException() {
        // given
        Menu menu = MenuFixture.create(MENU_NAME, TestUtil.PRICE,
            testUtil.getSavedMenuGroup(),
            testUtil.getMenuProducts(2),
            productPriceAdapter);
        MenuResponse savedMenuResponse = menuService.create(MenuFixture.toRequest(menu));

        UUID id = savedMenuResponse.id();
        MenuChangeRequest changeRequest = new MenuChangeRequest(TestUtil.MINUS_PRICE);

        // when then
        assertThatThrownBy(() -> menuService.changePrice(id, changeRequest))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴를 전시 상태로 변경할 수 있다.")
    @Test
    void display() {
        // given
        boolean displayed = false;
        Menu menu = MenuFixture.create(MENU_NAME, TestUtil.PRICE,
            testUtil.getSavedMenuGroup(),
            testUtil.getMenuProducts(2),
            productPriceAdapter);
        MenuResponse savedMenuResponse = menuService.create(MenuFixture.toRequest(menu));

        UUID id = savedMenuResponse.id();

        // when
        MenuResponse updatedMenuResponse = menuService.display(id);

        // then
        assertThat(updatedMenuResponse.displayed()).isTrue();
    }

    @DisplayName("메뉴를 숨김 상태로 변경할 수 있다.")
    @Test
    void hide() {
        // given
        Menu menu = MenuFixture.create(MENU_NAME, TestUtil.PRICE,
            testUtil.getSavedMenuGroup(),
            testUtil.getMenuProducts(2),
            productPriceAdapter);
        MenuResponse savedMenuResponse = menuService.create(MenuFixture.toRequest(menu));

        UUID id = savedMenuResponse.id();

        // when
        MenuResponse updatedMenuResponse = menuService.hide(id);

        // then
        assertThat(updatedMenuResponse.displayed()).isFalse();
    }

    @DisplayName("저장된 메뉴 리스트를 조회할 수 있다.")
    @Test
    void findAll() {
        // given
        Menu menu = MenuFixture.create(MENU_NAME, TestUtil.PRICE,
            testUtil.getSavedMenuGroup(),
            testUtil.getMenuProducts(2),
            productPriceAdapter);
        MenuResponse savedMenuResponse = menuService.create(MenuFixture.toRequest(menu));
        MenuResponse anotherSavedMenuResponse = menuService.create(MenuFixture.toRequest(menu));

        // when
        List<MenuResponse> savedMenus = menuService.findAll();

        // then
        assertThat(savedMenus)
            .hasSize(2)
            .extracting(MenuResponse::id)
            .containsExactlyInAnyOrder(anotherSavedMenuResponse.id(), savedMenuResponse.id());
    }
}
