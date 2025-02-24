package kitchenpos.menu.tobe.application;

import kitchenpos.common.exception.MenuException;
import kitchenpos.common.tobe.Profanities;
import kitchenpos.menu.tobe.domain.menu.*;
import kitchenpos.menu.tobe.domain.menugroup.MenuGroup;
import kitchenpos.menu.tobe.domain.menugroup.MenuGroupRepository;
import kitchenpos.menu.tobe.fake.FakePurogmalumClient;
import kitchenpos.menu.tobe.fake.InMemoryMenuGroupRepository;
import kitchenpos.menu.tobe.fake.InMemoryMenuRepository;
import kitchenpos.menu.tobe.fake.InMemoryProductRepository;
import kitchenpos.product.tobe.domain.Product;
import kitchenpos.product.tobe.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MenuServiceTest {

    private MenuService menuService;
    private MenuRepository menuRepository;
    private MenuGroupRepository menuGroupRepository;
    private ProductRepository productRepository;
    private Profanities profanities;
    private MenuValidator menuValidator;

    private UUID menuGroupId;
    private UUID productId1;
    private UUID productId2;
    private Product product1;
    private Product product2;

    @BeforeEach
    void setUp() {
        menuRepository = new InMemoryMenuRepository();
        menuGroupRepository = new InMemoryMenuGroupRepository();
        productRepository = new InMemoryProductRepository();
        profanities = new FakePurogmalumClient("바보", "멍청이");
        menuValidator = new MenuPriceValidator(productRepository);

        // 메뉴 그룹 생성
        menuGroupId = UUID.randomUUID();
        MenuGroup menuGroup = MenuGroup.of("음료");
        ReflectionTestUtils.setField(menuGroup, "id", menuGroupId);
        menuGroupRepository.save(menuGroup);

        // 상품 생성
        productId1 = UUID.randomUUID();
        productId2 = UUID.randomUUID();
        product1 = new Product("상품1", 5000L, profanities);
        product2 = new Product("상품2", 8000L, profanities);
        ReflectionTestUtils.setField(product1, "id", productId1);
        ReflectionTestUtils.setField(product2, "id", productId2);
        productRepository.save(product1);
        productRepository.save(product2);

        // 서비스 생성
        menuService = new MenuService(menuRepository, menuGroupRepository, profanities, menuValidator);
    }

    @Test
    @DisplayName("메뉴를 생성할 수 있다")
    void create() {
        // given
        MenuProduct menuProduct1 = new MenuProduct(null, 1, 5000L, productId1);
        MenuProduct menuProduct2 = new MenuProduct(null, 1, 8000L, productId2);
        List<MenuProduct> menuProducts = Arrays.asList(menuProduct1, menuProduct2);

        MenuRequest request = new MenuRequest(
                "아메리카노",
                13000L,
                menuGroupId,
                menuProducts,
                true
        );

        // when
        Menu created = menuService.create(request);

        // then
        assertThat(created).isNotNull();
        assertThat(created.getName()).isEqualTo("아메리카노");
        assertThat(created.getMenuPrice()).isEqualTo(13000L);
        assertThat(created.getMenuGroupId()).isEqualTo(menuGroupId);
        assertThat(created.isDisplayed()).isTrue();
        assertThat(created.getMenuProducts()).hasSize(2);
    }

    @Test
    @DisplayName("존재하지 않는 메뉴 그룹으로 메뉴를 생성할 수 없다")
    void createWithNonExistingMenuGroup() {
        // given
        UUID nonExistingMenuGroupId = UUID.randomUUID();
        MenuProduct menuProduct1 = new MenuProduct(null, 1, 5000L, productId1);
        MenuProduct menuProduct2 = new MenuProduct(null, 1, 8000L, productId2);
        List<MenuProduct> menuProducts = Arrays.asList(menuProduct1, menuProduct2);

        MenuRequest request = new MenuRequest(
                "아메리카노",
                13000L,
                nonExistingMenuGroupId,
                menuProducts,
                true
        );

        // when & then
        assertThatThrownBy(() -> menuService.create(request))
                .isInstanceOf(MenuException.class);
    }

    @Test
    @DisplayName("메뉴 가격을 변경할 수 있다")
    void changePrice() {
        // given
        MenuProduct menuProduct1 = new MenuProduct(null, 1, 5000L, productId1);
        MenuProduct menuProduct2 = new MenuProduct(null, 1, 8000L, productId2);
        List<MenuProduct> menuProducts = Arrays.asList(menuProduct1, menuProduct2);

        Menu menu = Menu.of("아메리카노", 13000L, menuGroupId, menuProducts, true, profanities, menuValidator);
        Menu saved = menuRepository.save(menu);

        MenuRequest request = new MenuRequest();
        request.setPrice(13000L);

        // when
        Menu updated = menuService.changePrice(saved.getId(), request);

        // then
        assertThat(updated.getMenuPrice()).isEqualTo(13000L);
    }

    @Test
    @DisplayName("메뉴를 display 상태로 변경할 수 있다")
    void display() {
        // given
        MenuProduct menuProduct1 = new MenuProduct(null, 1, 5000L, productId1);
        MenuProduct menuProduct2 = new MenuProduct(null, 1, 8000L, productId2);
        List<MenuProduct> menuProducts = Arrays.asList(menuProduct1, menuProduct2);

        Menu menu = Menu.of("아메리카노", 13000L, menuGroupId, menuProducts, false, profanities, menuValidator);
        Menu saved = menuRepository.save(menu);

        // when
        Menu displayed = menuService.display(saved.getId());

        // then
        assertThat(displayed.isDisplayed()).isTrue();
    }

    @Test
    @DisplayName("메뉴를 hide 상태로 변경할 수 있다")
    void hide() {
        // given
        MenuProduct menuProduct1 = new MenuProduct(null, 1, 5000L, productId1);
        MenuProduct menuProduct2 = new MenuProduct(null, 1, 8000L, productId2);
        List<MenuProduct> menuProducts = Arrays.asList(menuProduct1, menuProduct2);

        Menu menu = Menu.of("아메리카노", 13000L, menuGroupId, menuProducts, true, profanities, menuValidator);
        Menu saved = menuRepository.save(menu);

        // when
        Menu hidden = menuService.hide(saved.getId());

        // then
        assertThat(hidden.isDisplayed()).isFalse();
    }

    @Test
    @DisplayName("모든 메뉴를 조회할 수 있다")
    void findAll() {
        // given
        MenuProduct menuProduct1 = new MenuProduct(null, 1, 5000L, productId1);
        MenuProduct menuProduct2 = new MenuProduct(null, 1, 8000L, productId2);
        List<MenuProduct> menuProducts = Arrays.asList(menuProduct1, menuProduct2);

        Menu menu1 = Menu.of("아메리카노", 13000L, menuGroupId, menuProducts, true, profanities, menuValidator);
        Menu menu2 = Menu.of("카페라떼", 13000L, menuGroupId, menuProducts, true, profanities, menuValidator);

        menuRepository.save(menu1);
        menuRepository.save(menu2);

        // when
        List<Menu> menus = menuService.findAll();

        // then
        assertThat(menus).hasSize(2);
    }
}