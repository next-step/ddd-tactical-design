package kitchenpos.menus.tobe.application;

import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.menus.tobe.domain.menu.MenuRepository;
import kitchenpos.products.tobe.application.InMemoryProductRepository;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static kitchenpos.ToBeFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("메뉴명 노출 서비스")
class MenuDisplayServiceTest {

    private MenuDisplayService menuDisplayService;
    private Menu menu;

    @BeforeEach
    void setUp() {
        ProductRepository productRepository = new InMemoryProductRepository();
        MenuRepository menuRepository = new InMemoryMenuRepository();
        menuDisplayService = new MenuDisplayService(menuRepository);
        Product product = productRepository.save(product("후라이드", 16_000L));
        menu = menuRepository.save(menu("후라이드치킨", 19_000L, true, menuProduct(product, 2L)));
    }

    @DisplayName("메뉴를 노출할 수 있다.")
    @Test
    void display() {
        final Menu actual = menuDisplayService.hide(menu.getId());
        assertThat(actual.isDisplayed()).isFalse();
        menuDisplayService.display(menu.getId());
        assertThat(actual.isDisplayed()).isTrue();
    }

    @DisplayName("메뉴를 숨길 수 있다.")
    @Test
    void hide() {
        final Menu actual = menuDisplayService.hide(menu.getId());
        assertThat(actual.isDisplayed()).isFalse();
    }

}
