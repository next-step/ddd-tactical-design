package kitchenpos.menus.application;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;
import kitchenpos.menus.domain.MenuGroupRepository;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.menus.exception.MenuErrorCode;
import kitchenpos.menus.exception.MenuException;
import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuGroup;
import kitchenpos.menus.tobe.domain.MenuGroupName;
import kitchenpos.menus.tobe.domain.MenuName;
import kitchenpos.menus.tobe.domain.MenuPrice;
import kitchenpos.menus.tobe.domain.MenuProduct;
import kitchenpos.menus.tobe.domain.MenuProductQuantity;
import kitchenpos.menus.tobe.domain.MenuProducts;
import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.application.InMemoryProductRepository;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductName;
import kitchenpos.products.tobe.domain.ProductPrice;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


class MenuServiceTest {

    private final PurgomalumClient purgomalumClient = new FakePurgomalumClient();
    private final MenuGroupRepository menuGroupRepository= new InMemoryMenuGroupRepository();
    private final MenuRepository menuRepository= new InMemoryMenuRepository();
    private final ProductRepository productRepository= new InMemoryProductRepository();
    private MenuService menuService = new MenuService(menuRepository, menuGroupRepository, productRepository, purgomalumClient);

    @Test
    @DisplayName("메뉴명에 욕설이 포함되면 예외가 발생한다.")
    public void 메뉴명_욕설() throws Exception {
        MenuGroup menuGroup = MenuGroup.of(new MenuGroupName("메뉴그룹이름"));

        assertThatThrownBy(() -> Menu.of(new MenuName("욕설", purgomalumClient),
            new MenuPrice(BigDecimal.valueOf(16_000)), menuGroup, true, null))
            .isInstanceOf(new MenuException(MenuErrorCode.NAME_CONTAINS_PROFANITY).getClass());
    }

    @Test
    @DisplayName("메뉴가격을 변경하려면 메뉴상풍의 총 가격이 상품의 총 가격보다 낮아야 한다.")
    public void 메뉴가격변경_에러() throws Exception {
        Menu menu = setMenu();
        menu.changePrice(new MenuPrice(BigDecimal.valueOf(70_000)));
        assertThatThrownBy(() -> menuService.changePrice(menu.getId(),menu)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("메뉴가격을 변경하려면 메뉴상풍의 총 가격이 상품의 총 가격보다 낮아야 한다.")
    public void 메뉴가격변경_성공() throws Exception {
        Menu menu = setMenu();
        menu.changePrice(new MenuPrice(BigDecimal.valueOf(50_000)));
        Menu changedMenu = menuService.changePrice(menu.getId(),menu);
        assertThat(changedMenu.getPrice()).isEqualTo(BigDecimal.valueOf(50_000));
    }

    @Test
    @DisplayName("메뉴_활성화")
    public void 메뉴_활성화() throws Exception {
        Menu menu = setMenu();
        menu.changeDisplay(false);
        Menu changedMenu = menuService.display(menu.getId());
        assertThat(changedMenu.isDisplayed()).isTrue();
    }

    @Test
    @DisplayName("메뉴_비활성화")
    public void 메뉴_비활성화() throws Exception {
        Menu menu = setMenu();
        Menu changedMenu = menuService.hide(menu.getId());
        assertThat(changedMenu.isDisplayed()).isFalse();
    }

    public Menu setMenu() {
        MenuGroup menuGroup = MenuGroup.of(new MenuGroupName("메뉴그룹이름"));
        Product product_10000 = Product.of(new ProductName("상품이름1", purgomalumClient), new ProductPrice(BigDecimal.valueOf(10_000)));
        Product product_20000 = Product.of(new ProductName("상품이름2", purgomalumClient), new ProductPrice(BigDecimal.valueOf(20_000)));
        Product product_30000 = Product.of(new ProductName("상품이름3", purgomalumClient), new ProductPrice(BigDecimal.valueOf(30_000)));
        product_10000 = productRepository.save(product_10000);
        product_20000 = productRepository.save(product_20000);
        product_30000 = productRepository.save(product_30000);
        MenuProducts menuProducts = new MenuProducts(List.of(new MenuProduct(product_10000.getId(), new MenuProductQuantity(1)),
            new MenuProduct(product_20000.getId(),new MenuProductQuantity(1)),new MenuProduct(product_30000.getId(),new MenuProductQuantity(1))));
        Menu menu = Menu.of(new MenuName("메뉴이름", purgomalumClient), new MenuPrice(BigDecimal.valueOf(20_000)), menuGroup, true, menuProducts);

        return menuRepository.save(menu);
    }

}