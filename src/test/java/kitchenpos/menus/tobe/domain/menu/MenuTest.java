package kitchenpos.menus.tobe.domain.menu;

import kitchenpos.menus.application.InMemoryMenuGroupRepository;
import kitchenpos.menus.dto.MenuProductCreateRequest;
import kitchenpos.menus.exception.InvalidMenuPriceDisplayException;
import kitchenpos.menus.exception.InvalidMenuPriceException;
import kitchenpos.menus.tobe.domain.menugroup.MenuGroup;
import kitchenpos.menus.tobe.domain.menugroup.MenuGroupName;
import kitchenpos.menus.tobe.domain.menugroup.MenuGroupRepository;
import kitchenpos.menus.tobe.infra.ProductClientImpl;
import kitchenpos.products.application.InMemoryProductRepository;
import kitchenpos.products.tobe.domain.ProductRepository;
import kitchenpos.support.domain.MenuPrice;
import kitchenpos.support.domain.ProductPrice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static kitchenpos.fixture.ProductFixture.product;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("메뉴")
class MenuTest {
    private static UUID productA_ID;
    private static MenuName name;
    private static MenuPrice price;
    private static MenuGroup menuGroup;
    private static MenuProducts menuProducts;

    List<MenuProductCreateRequest> menuProductsRequest;

    @BeforeEach
    void setUp() {
        ProductRepository productRepository = new InMemoryProductRepository();
        MenuGroupRepository menuGroupRepository = new InMemoryMenuGroupRepository();
        ProfanityChecker profanityChecker = new FakeProfanityChecker();
        ProductClient productClient = new ProductClientImpl(productRepository);

        menuGroup = menuGroupRepository.save(MenuGroup.from(MenuGroupName.from("메뉴그룹 이름")));
        productA_ID = productRepository.save(product("후라이드치킨", 10_000)).getId();
        UUID productB_ID = productRepository.save(product("양념치킨", 12_000)).getId();
        MenuProductCreateRequest menuProductA = new MenuProductCreateRequest(productA_ID, 1L);
        MenuProductCreateRequest menuProductB = new MenuProductCreateRequest(productB_ID, 2L);
        menuProductsRequest = List.of(menuProductA, menuProductB);
        name = MenuName.from("메뉴 이름", profanityChecker);
        price = MenuPrice.from(30_000);
        menuProducts = MenuProducts.from(menuProductsRequest, productClient);
    }


    @DisplayName("[성공] 메뉴를 생성한다.")
    @Test
    void create() {
        Menu actual = Menu.of(name, price, menuGroup, true, menuProducts);

        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getName()).isEqualTo("메뉴 이름"),
                () -> assertThat(actual.getPrice()).isEqualTo(BigDecimal.valueOf(30_000L)),
                () -> assertThat(actual.getMenuGroup().getName()).isEqualTo("메뉴그룹 이름"),
                () -> assertThat(actual.isDisplayed()).isTrue(),
                () -> assertThat(actual.getMenuProductList()).hasSize(2)
        );
    }

    @DisplayName("[실패] 메뉴의 가격이 메뉴구성상품의 합보다 크면 메뉴가 생성되지 않는다.")
    @ValueSource(longs = {34_001, 50_000, 100_000})
    @ParameterizedTest
    void fail_create(long price) {
        MenuPrice invalidPrice = MenuPrice.from(price);
        assertThatThrownBy(() -> Menu.of(name, invalidPrice, menuGroup, true, menuProducts))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("[성공] 메뉴의 가격을 변경한다.")
    @Test
    void changePrice() {
        Menu menu = Menu.of(name, price, menuGroup, true, menuProducts);
        MenuPrice newPrice = MenuPrice.from(29_900);

        menu.changePrice(newPrice);

        assertThat(menu.getPrice()).isEqualTo(BigDecimal.valueOf(29_900));
    }

    @DisplayName("[실패] 메뉴의 가격은 메뉴구성상품의 가격의 총합보다 작거나 같아야 변경할 수 있다.")
    @ValueSource(longs = {34_001, 50_000, 100_000})
    @ParameterizedTest
    void fail_changePrice(long newPrice) {
        MenuPrice invalidPrice = MenuPrice.from(newPrice);
        Menu menu = Menu.of(name, price, menuGroup, true, menuProducts);

        assertThatThrownBy(() -> menu.changePrice(invalidPrice))
                .isInstanceOf(InvalidMenuPriceException.class);
    }

    @DisplayName("[성공] 메뉴를 구성하는 메뉴구성상품의 가격을 변경한다.")
    @Test
    void changeMenuProductPrice() {
        Menu menu = Menu.of(name, price, menuGroup, true, menuProducts);
        BigDecimal newPrice = BigDecimal.valueOf(11_000);

        menu.changeMenuProductPrice(productA_ID, newPrice);

        MenuProduct menuProduct = menu.getMenuProducts().getMenuProductByProductId(productA_ID);
        assertAll(
                () -> assertThat(menuProduct.getPrice()).isEqualTo(ProductPrice.from(11_000)),
                () -> assertThat(menu.getMenuProducts().sumPrice()).isEqualTo(BigDecimal.valueOf(35_000))
        );
    }

    @DisplayName("[성공] 메뉴의 가격은 메뉴구성상품의 가격의 총합보다 크다.")
    @Test
    void isPriceGreaterThanMenuProductsSum() {
        Menu menu = Menu.of(name, price, menuGroup, true, menuProducts);
        BigDecimal newProductPrice = BigDecimal.valueOf(5_000);
        menu.changeMenuProductPrice(productA_ID, newProductPrice);

        assertThat(menu.isPriceGreaterThanMenuProductsSum()).isTrue();
    }

    @DisplayName("[성공] 메뉴를 노출시킨다.")
    @Test
    void display() {
        Menu menu = Menu.of(name, price, menuGroup, false, menuProducts);

        menu.display();

        assertThat(menu.isDisplayed()).isTrue();
    }

    @DisplayName("[실패] 메뉴의 가격이 메뉴구성상품의 가격의 총합보다 크면 메뉴는 노출될 수 없다.")
    @Test
    void fail_display() {
        Menu menu = Menu.of(name, price, menuGroup, false, menuProducts);
        BigDecimal invalidProductPrice = BigDecimal.valueOf(5_000);
        menu.changeMenuProductPrice(productA_ID, invalidProductPrice);

        assertThatThrownBy(menu::display)
                .isInstanceOf(InvalidMenuPriceDisplayException.class);
    }

    @DisplayName("[성공] 메뉴가 숨겨진다.")
    @Test
    void hide() {
        Menu menu = Menu.of(name, price, menuGroup, true, menuProducts);

        menu.hide();

        assertThat(menu.isDisplayed()).isFalse();
    }
}
