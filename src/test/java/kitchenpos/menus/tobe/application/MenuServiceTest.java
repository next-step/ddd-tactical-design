package kitchenpos.menus.tobe.application;

import kitchenpos.ToBeFixtures;
import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.menus.tobe.domain.menu.MenuRepository;
import kitchenpos.products.tobe.application.InMemoryProductRepository;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static kitchenpos.ToBeFixtures.menu;
import static kitchenpos.ToBeFixtures.menuProduct;
import static org.assertj.core.api.Assertions.assertThat;

class MenuServiceTest {

    private MenuRepository menuRepository;
    private MenuService menuService;
    private Product product;
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = new InMemoryProductRepository();
        menuRepository = new InMemoryMenuRepository();
        menuService = new MenuService(menuRepository);
        product = productRepository.save(ToBeFixtures.product("후라이드", 16_000L));
    }

    @DisplayName("메뉴를 노출할 수 있다.")
    @Test
    void display() {
        final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct(product, 2L))).getId();
        final Menu actual = menuService.hide(menuId);
        assertThat(actual.isDisplayed()).isFalse();
        menuService.display(menuId);
        assertThat(actual.isDisplayed()).isTrue();
    }

    @DisplayName("메뉴를 숨길 수 있다.")
    @Test
    void hide() {
        final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct(product, 2L))).getId();
        final Menu actual = menuService.hide(menuId);
        assertThat(actual.isDisplayed()).isFalse();
    }

}
