package kitchenpos.menus.tobe.application;

import static java.math.BigDecimal.valueOf;
import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuGroup;
import kitchenpos.menus.tobe.domain.MenuProduct;
import kitchenpos.menus.tobe.domain.MenuRepository;
import kitchenpos.menus.tobe.domain.vo.MenuProducts;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.event.ProductPriceChangedEvent;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

class MenuServiceTest {
    private MenuService menuService;
    private MenuRepository menuRepository;

    @BeforeEach
    void setUp() {
        menuRepository = new InMemoryMenuRepository();
        menuService = new MenuService(menuRepository);
    }

    @DisplayName("상품 가격이 변경되어 메뉴 가격보다 낮아지면 메뉴의 전시 상태가 숨김 처리된다")
    @Test
    void 상품_가격이_변경되면_메뉴의_전시_상태가_숨김_처리된다() {
        // given
        Product product = new Product("후라이드치킨", valueOf(20_000));
        MenuProduct menuProduct = new MenuProduct(product, 20_000, 1, product.getId());
        MenuProducts menuProducts = new MenuProducts(List.of(menuProduct));

        Menu menu = new Menu(
                new MenuGroup("메인메뉴"),
                "후라이드치킨",
                20_000,
                true,
                menuProducts,
                name -> false // profanity 체크 X
        );

        menuRepository.save(menu);

        // 상품 가격을 낮춰 메뉴 가격보다 작게 만듦
        product.updatePrice(valueOf(10_000));

        // when
        ProductPriceChangedEvent event = new ProductPriceChangedEvent(product.getId(), valueOf(10_000));
        menuService.handleProductPriceChanged(event);
        List<Menu> updatedMenus = menuRepository.findAllByProductId(product.getId());

        // then
        assertThat(updatedMenus).hasSize(1);
        assertThat(updatedMenus.get(0).isDisplayed()).isFalse(); // 메뉴 가격이 더 높아졌으므로 숨겨져야 함
    }
}
