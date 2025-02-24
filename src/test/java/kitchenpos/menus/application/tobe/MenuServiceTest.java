package kitchenpos.menus.application.tobe;

import kitchenpos.common.vo.Price;
import kitchenpos.menus.infra.InMemoryMenuGroupRepository;
import kitchenpos.menus.infra.InMemoryMenuRepository;
import kitchenpos.menus.infra.MenuProductsValidatorService;
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
        MenuProducts menuProducts = new MenuProducts(
                new MenuProduct(CHICKEN, 1, 25_000),
                new MenuProduct(COKE, 1, 2_000)
        );
        Menu menu = createMenu(
                MenuId.generate(),
                "후라이드치킨세트",
                27_000,
                MENU_GROUP,
                menuProducts
        );

        Menu result = menuService.create(menu);

        assertAll(
                () -> assertThat(result.getGroupId()).isEqualTo(menu.getGroupId()),
                () -> assertThat(result.getMenuProducts()).isEqualTo(menuProducts),
                () -> assertThat(result.getName()).isEqualTo(new MenuName("후라이드치킨세트", (name) -> false)),
                () -> assertThat(result.getPrice()).isEqualTo(new Price(27_000))
        );
    }

    @DisplayName("등록되지 않은 상품으로 메뉴를 등록 시 예외가 발생한다")
    @Test
    void invalidMenuProduct() {
        MenuProducts menuProducts = new MenuProducts(
                new MenuProduct(CHICKEN, 1, 25_000),
                new MenuProduct(ProductId.generate(), 1, 2_000)
        );
        Menu menu = createMenu(
                MenuId.generate(),
                "후라이드치킨세트",
                27_000,
                MENU_GROUP,
                menuProducts
        );

        assertThatThrownBy(() -> menuService.create(menu))
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

        menu.changePrice(new Price(26_000));
        Menu result = menuService.changePrice(id, menu);

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