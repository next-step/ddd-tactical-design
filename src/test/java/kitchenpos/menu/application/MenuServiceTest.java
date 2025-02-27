package kitchenpos.menu.application;

import static kitchenpos.TestFixtureFactory.createMenuWithProductAndGroup;
import static kitchenpos.TestFixtureFactory.createProduct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import kitchenpos.common.application.PurgomalumClient;
import kitchenpos.common.infra.external.FakePurgomalumClient;
import kitchenpos.menu.application.dto.ChangeMenuPriceServiceRq;
import kitchenpos.menu.application.dto.CreateMenuServiceRq;
import kitchenpos.menu.application.dto.CreateMenuServiceRq.MenuProductServiceRq;
import kitchenpos.menu.application.dto.MenuServiceRs;
import kitchenpos.menu.application.dto.SimpleMenuServiceRs;
import kitchenpos.menu.domain.model.Menu;
import kitchenpos.menu.domain.model.MenuGroup;
import kitchenpos.menu.domain.model.MenuNameCreationService;
import kitchenpos.menu.domain.repository.MenuGroupRepository;
import kitchenpos.menu.domain.repository.MenuRepository;
import kitchenpos.menu.domain.service.MarginValidator;
import kitchenpos.menu.domain.service.MenuProductValidator;
import kitchenpos.menu.infra.persistence.FakeMenuGroupRepository;
import kitchenpos.menu.infra.persistence.FakeMenuRepository;
import kitchenpos.product.domain.model.Product;
import kitchenpos.product.domain.repository.ProductRepository;
import kitchenpos.product.infra.persistence.FakeProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class MenuServiceTest {

    private MenuService menuService;
    private MenuRepository menuRepository;
    private MenuGroupRepository menuGroupRepository;
    private ProductRepository productRepository;
    private PurgomalumClient purgomalumClient;

    @BeforeEach
    void setUp() {
        menuRepository = new FakeMenuRepository(new HashMap<>());
        menuGroupRepository = new FakeMenuGroupRepository(new HashMap<>());
        productRepository = new FakeProductRepository(new HashMap<>());
        purgomalumClient = new FakePurgomalumClient();
        MarginValidator marginValidator = new MarginValidator(menuRepository);
        MenuProductValidator menuProductValidator = new MenuProductValidator(productRepository);
        MenuNameCreationService menuNameCreationService = new MenuNameCreationService(purgomalumClient);
        menuService = new MenuService(menuRepository, menuGroupRepository, productRepository, marginValidator,
                menuProductValidator, menuNameCreationService);
    }

    @Test
    @DisplayName("메뉴를 생성한다")
    void create_menu() {
        // given
        MenuGroup menuGroup = createMenuGroup();
        menuGroupRepository.save(menuGroup);
        Product product = createProduct(BigDecimal.valueOf(5000));
        productRepository.save(product);
        CreateMenuServiceRq request = createMenuServiceRequest("김치찌개", 12000, menuGroup.getId(), product.getId());

        // when
        MenuServiceRs result = menuService.create(request);

        // then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getName()).isEqualTo("김치찌개");
        assertThat(result.getPrice()).isEqualTo(BigDecimal.valueOf(12000));
        assertThat(result.getMenuGroupId()).isEqualTo(menuGroup.getId());
        assertThat(menuRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("메뉴 가격은 0원 미만이면 예외가 발생한다.")
    void menu_price_exception() {
        // given
        MenuGroup menuGroup = createAndSaveMenuGroup();
        Product product = createAndSaveProduct();
        CreateMenuServiceRq request = createMenuServiceRequest("김치찌개", -1000, menuGroup.getId(),
                product.getId());

        // when // then
        assertThatThrownBy(() -> {
            menuService.create(request);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("메뉴에 이름이 없으면 예외가 발생한다.")
    void menu_name_exists_exception(String name) {
        // given
        MenuGroup menuGroup = createMenuGroup();
        menuGroupRepository.save(menuGroup);
        Product product = createProduct(BigDecimal.valueOf(5000));
        productRepository.save(product);
        CreateMenuServiceRq request = createMenuServiceRequest(name, 8000, menuGroup.getId(),
                product.getId());

        // when // then
        assertThatThrownBy(() -> {
            menuService.create(request);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("메뉴 이름에 비속어가 포함되면 예외가 발생한다")
    void menu_name_profanity_exception() {
        // given
        MenuGroup menuGroup = createMenuGroup();
        menuGroupRepository.save(menuGroup);
        Product product = createProduct(BigDecimal.valueOf(5000));
        productRepository.save(product);
        CreateMenuServiceRq request = createMenuServiceRequest("fuck", 8000, menuGroup.getId(),
                product.getId());

        // 다운 캐스팅해서 강제로 메소드 호출
        FakePurgomalumClient fakePurgomalumClient = (FakePurgomalumClient) purgomalumClient;
        fakePurgomalumClient.setProfanity(true);

        // when // then
        assertThatThrownBy(() -> menuService.create(request))
                .isInstanceOf(IllegalArgumentException.class);
    }

    /**
     * 필요 없어진 테스트
     */
//    @Test
//    @DisplayName("메뉴 그룹이 존재하지 않으면 예외가 발생한다")
//    void menu_group_exception() {
//        // given
//        Product product = createProduct(BigDecimal.valueOf(5000));
//        MenuProduct menuProduct = new MenuProduct(1, product, product.getId());
//        CreateMenuServiceRq request = createMenuServiceRequest("김치찌개", 8000, UUID.randomUUID(), menuProduct.getProductId());
//
//        // when // then
//        assertThatThrownBy(() -> {
//            menuService.create(request);
//        }).isInstanceOf(NoSuchElementException.class)
//                .hasMessage("메뉴 그룹이 존재하지 않습니다!");
//    }
    @Test
    @DisplayName("메뉴의 판매 가격이 재료 가격의 총합 낮으면 예외가 발생한다.")
    void create_menu_with_menuPrice_andTotalPrice_exception() {
        // given
        MenuGroup menuGroup = createMenuGroup();
        menuGroupRepository.save(menuGroup);
        Product product = createProduct(BigDecimal.valueOf(10000));
        productRepository.save(product);
        CreateMenuServiceRq request = createMenuServiceRequest("김치찌개", 8000, menuGroup.getId(), product.getId());

        // when // then
        assertThatThrownBy(() -> {
            menuService.create(request);
        }).isInstanceOf(IllegalStateException.class)
                .hasMessage("마진이 남지 않습니다! 마진을 남기게 만들어주세요!");
    }

    @Test
    @DisplayName("메뉴의 가격을 변경할 수 있다")
    void change_price() {
        // given
        Menu menu = createMenuWithProductAndGroup();
        menuRepository.save(menu);

        ChangeMenuPriceServiceRq request = new ChangeMenuPriceServiceRq(BigDecimal.valueOf(12000));

        // when
        SimpleMenuServiceRs result = menuService.changePrice(menu.getId(), request);

        // then
        assertThat(result.getPrice()).isEqualTo(BigDecimal.valueOf(12000));
    }

    @Test
    @DisplayName("변경하려는 가격이 재료 가격의 총합보다 낮으면 예외가 발생한다")
    void change_price_with_menuPrice_andTotalPrice_exception() {
        // given
        Menu menu = createMenuWithProductAndGroup();
        menuRepository.save(menu);

        ChangeMenuPriceServiceRq request = new ChangeMenuPriceServiceRq(BigDecimal.valueOf(4000));

        // when // then
        assertThatThrownBy(() -> {
            menuService.changePrice(menu.getId(), request);
        }).isInstanceOf(IllegalStateException.class)
                .hasMessage("마진이 남지 않습니다! 마진을 남기게 만들어주세요!");
    }

    @Test
    @DisplayName("메뉴를 표시 상태로 변경할 수 있다")
    void display() {
        // given
        Menu menu = createMenuWithProductAndGroup();
        menuRepository.save(menu);

        // when
        SimpleMenuServiceRs result = menuService.display(menu.getId());

        // then
        assertThat(result.isDisplayed()).isTrue();
    }

    @Test
    @DisplayName("메뉴를 숨김 상태로 변경할 수 있다")
    void hide() {
        // given
        Menu menu = createMenuWithProductAndGroup();
        menu.changeDisplay();
        menuRepository.save(menu);

        // when
        SimpleMenuServiceRs result = menuService.hide(menu.getId());

        // then
        assertThat(result.isDisplayed()).isFalse();
    }


    @Test
    @DisplayName("전체 메뉴를 조회할 수 있다")
    void find_allMenus() {
        // given
        List<Menu> menus = List.of(
                createMenuWithProductAndGroup(), createMenuWithProductAndGroup());
        for (Menu menu : menus) {
            menuRepository.save(menu);
        }

        // when
        List<MenuServiceRs> result = menuService.findAll();

        // then
        assertThat(result).hasSize(2);
    }

    private MenuGroup createMenuGroup() {
        return new MenuGroup("한식");
    }

    private CreateMenuServiceRq createMenuServiceRequest(String name, int price, UUID menuGroupId, UUID productId) {
        return new CreateMenuServiceRq(name, BigDecimal.valueOf(price), true, menuGroupId,
                List.of(new MenuProductServiceRq(productId, 2)));
    }

    private MenuGroup createAndSaveMenuGroup() {
        return menuGroupRepository.save(createMenuGroup());
    }

    private Product createAndSaveProduct() {
        return productRepository.save(createProduct("김치", 5000));
    }
}
