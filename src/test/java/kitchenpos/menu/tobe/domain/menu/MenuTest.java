package kitchenpos.menu.tobe.domain.menu;

import kitchenpos.menu.tobe.domain.menu.validate.ProductValidator;
import kitchenpos.menu.tobe.domain.menu.validate.ProfanityValidator;
import kitchenpos.menu.tobe.domain.menugroup.MenuGroup;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@DisplayName("Menu 도메인 테스트")
class MenuTest {
    private Menu menu;
    private MenuProducts menuProducts;
    private ProfanityValidator profanityValidator;
    private ProductValidator productValidator;

    @BeforeEach
    void setUp() {
        profanityValidator = Mockito.mock(ProfanityValidator.class);
        productValidator = Mockito.mock(ProductValidator.class);

        UUID menuId = UUID.randomUUID();
        MenuName menuName = new MenuName("메뉴명", profanityValidator);
        MenuPrice menuPrice = new MenuPrice(BigDecimal.valueOf(1000L));
        MenuGroup menuGroup = Mockito.mock(MenuGroup.class);

        MenuProduct product1 = new MenuProduct(UUID.randomUUID(), 2L, new BigDecimal(500L));
        menuProducts = new MenuProducts(List.of(product1), menuPrice, productValidator);

        menu = new Menu(menuId, menuName, menuPrice, menuGroup, true, menuProducts);
    }

    @Test
    @DisplayName("메뉴 가격을 변경할 수 있다.")
    void test1() {
        BigDecimal newPrice = BigDecimal.valueOf(300L);
        menu.changePrice(newPrice);

        Assertions.assertThat(newPrice).isEqualTo(menu.getPrice());
    }

    @Test
    @DisplayName("메뉴 내 상품 가격을 변경할 수 있다.")
    void test2() {
        UUID productId = menuProducts.getMenuProducts().get(0).getProductId();
        ProductPrice newPrice = new ProductPrice(BigDecimal.valueOf(500L));

        menu.changeMenuProductPrice(productId, newPrice);

        Assertions.assertThat(newPrice.getPrice()).isEqualTo(menuProducts.getMenuProducts().get(0).getPrice());
    }

    @Test
    @DisplayName("메뉴 가격이 속해있는 상품들의 총 합계 가격보다 높을 경우, 자동으로 메뉴를 숨김 상태로 설정한다.")
    void test3() {
        UUID productId = menuProducts.getMenuProducts().get(0).getProductId();
        ProductPrice newPrice = new ProductPrice(BigDecimal.valueOf(100L));

        menu.changeMenuProductPrice(productId, newPrice);

        Assertions.assertThat(menu.isDisplayed()).isFalse();
    }

    @Test
    @DisplayName("메뉴를 숨길 수 있다.")
    void test4() {
        menu.hide();

        Assertions.assertThat(menu.isDisplayed()).isFalse();
    }
}
