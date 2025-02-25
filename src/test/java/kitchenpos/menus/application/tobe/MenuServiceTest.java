package kitchenpos.menus.application.tobe;

import kitchenpos.common.vo.Price;
import kitchenpos.menus.infra.InMemoryMenuGroupRepository;
import kitchenpos.menus.infra.InMemoryMenuRepository;
import kitchenpos.menus.infra.MenuProductsValidatorService;
import kitchenpos.menus.presentation.dto.*;
import kitchenpos.menus.tobe.domain.*;
import kitchenpos.menus.tobe.domain.exception.InvalidMenuProductsException;
import kitchenpos.products.infra.tobe.InMemoryProductRepository;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductId;
import kitchenpos.products.tobe.domain.ProductName;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class MenuServiceTest {
    private static final ProductId CHICKEN = ProductId.generate();
    private static final ProductId COKE = ProductId.generate();
    private static final MenuGroupId MENU_GROUP = MenuGroupId.generate();

    private ProductRepository productRepository;
    private MenuGroupRepository menuGroupRepository;

    private MenuRepository menuRepository;
    private MenuProductsValidator menuProductsValidator;
    private MenuService menuService;

    @BeforeEach
    void setUp() {
        this.productRepository = new InMemoryProductRepository();
        this.menuGroupRepository = new InMemoryMenuGroupRepository();
        this.menuRepository = new InMemoryMenuRepository();
        this.menuProductsValidator = new MenuProductsValidatorService(this.productRepository);
        this.menuService = new MenuService(menuRepository, menuGroupRepository, menuProductsValidator);

        productRepository.save(createProduct(CHICKEN, "후라이드치킨", 25_000));
        productRepository.save(createProduct(COKE, "제로콜라", 2_000));

        menuGroupRepository.save(createMenuGroup(MENU_GROUP, "치킨"));
    }

    @DisplayName("메뉴를 생성할 수 있다")
    @Test
    void create() {
        List<MenuProduct> products = List.of(
                new MenuProduct(CHICKEN, 1, 25_000),
                new MenuProduct(COKE, 1, 2_000)
        );
        MenuCreateRequest request = new MenuCreateRequest(
                "후라이드치킨세트",
                (name) -> false,
                27_000,
                MENU_GROUP.value(),
                true,
                products
        );

        MenuCreateResponse result = menuService.create(request);

        assertAll(
                () -> assertThat(result.getMenuGroupId()).isEqualTo(new MenuGroupId(request.getMenuGroupId())),
                () -> assertThat(result.getProducts()).isEqualTo(new MenuProducts(products)),
                () -> assertThat(result.getName()).isEqualTo(new MenuName("후라이드치킨세트", (name) -> false)),
                () -> assertThat(result.getPrice()).isEqualTo(new Price(27_000))
        );
    }

    @DisplayName("등록되지 않은 상품으로 메뉴를 등록 시 예외가 발생한다")
    @Test
    void invalidMenuProduct() {
        List<MenuProduct> products = List.of(
                new MenuProduct(CHICKEN, 1, 25_000),
                new MenuProduct(ProductId.generate(), 1, 2_000)
        );
        MenuCreateRequest request = new MenuCreateRequest(
                "후라이드치킨세트",
                (name) -> false,
                27_000,
                MENU_GROUP.value(),
                true,
                products
        );

        assertThatThrownBy(() -> menuService.create(request))
                .isInstanceOf(InvalidMenuProductsException.class);
    }

    @DisplayName("메뉴의 가격을 변경한다")
    @Test
    void changePrice() {
        MenuProducts menuProducts = new MenuProducts(
                new MenuProduct(CHICKEN, 1, 25_000),
                new MenuProduct(COKE, 1, 2_000)
        );
        MenuId id = MenuId.generate();
        Menu menu = menuRepository.save(createMenu(
                id,
                "후라이드치킨세트",
                27_000,
                MENU_GROUP,
                menuProducts
        ));
        MenuChangePriceRequest request = new MenuChangePriceRequest(id.value(), 26_000);

        MenuChangePriceResponse result = menuService.changePrice(request);

        assertThat(result.getPrice()).isEqualTo(new Price(26_000));
    }

    @DisplayName("메뉴의 노출여부를 true로 변경한다")
    @Test
    void show() {
        MenuProducts menuProducts = new MenuProducts(
                new MenuProduct(CHICKEN, 1, 25_000),
                new MenuProduct(COKE, 1, 2_000)
        );
        MenuId id = MenuId.generate();
        Menu menu = menuRepository.save(createMenu(
                id,
                "후라이드치킨세트",
                27_000,
                MENU_GROUP,
                menuProducts
        ));

        Menu result = menuService.display(id);

        assertThat(result.isDisplayed()).isTrue();
    }

    @DisplayName("메뉴의 노출여부를 false로 변경한다")
    @Test
    void hide() {
        MenuProducts menuProducts = new MenuProducts(
                new MenuProduct(CHICKEN, 1, 25_000),
                new MenuProduct(COKE, 1, 2_000)
        );
        MenuId id = MenuId.generate();
        Menu menu = menuRepository.save(createMenu(
                id,
                "후라이드치킨세트",
                27_000,
                MENU_GROUP,
                menuProducts
        ));

        Menu result = menuService.hide(id);

        assertThat(result.isDisplayed()).isFalse();
    }

    @DisplayName("메뉴들 중 특정 메뉴 상품의 가격을 변경한다. 변경 후, 메뉴 가격이 메뉴 상품 총 합보다 크면 비전시된 메뉴가 된다")
    @Test
    void changeProductPrice() {
        MenuId id = MenuId.generate();
        MenuProducts menuProducts = new MenuProducts(
                new MenuProduct(CHICKEN, 1, 25_000),
                new MenuProduct(COKE, 1, 2_000)
        );
        menuRepository.save(createMenu(id, "후라이드치킨세트1", 27_000, MENU_GROUP, menuProducts));

        MenuProductPriceChangeResponse result = menuService.changeProductPrice(new MenuProductPriceChangeRequest(CHICKEN, new Price(24_000)));

        assertThat(result.getResults()).hasSize(1);
        assertThat(result.getResults().getFirst())
                .extracting("id", "displayed")
                        .contains(id, false);
    }

    private MenuGroup createMenuGroup(MenuGroupId id, String name) {
        return new MenuGroup(id, new MenuGroupName(name));
    }

    private Product createProduct(ProductId id, String name, int price) {
        return new Product(id, new ProductName(name, (productName) -> false), new Price(price));
    }

    private Menu createMenu(MenuId id, String name, int price, MenuGroupId menuGroupId, MenuProducts menuProducts) {
        return new Menu(
                id,
                new MenuName(name, (menuName) -> false),
                new Price(price),
                menuGroupId,
                menuProducts,
                true
        );
    }
}