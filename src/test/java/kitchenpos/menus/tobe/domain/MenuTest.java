package kitchenpos.menus.tobe.domain;

import kitchenpos.common.domain.Price;
import kitchenpos.menus.application.FakeMenuProfanityPolicy;
import kitchenpos.common.exception.PriceException;
import kitchenpos.products.tobe.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;

import static kitchenpos.menus.application.fixtures.MenuGroupFixture.menuGroup;
import static kitchenpos.products.fixture.ProductFixture.product;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("메뉴")
class MenuTest {

    private MenuProduct menuProduct_1000;
    private MenuProduct menuProduct_2000;
    private MenuProducts menuProducts;
    private Menu menu;
    private Product product_1000;
    private Product product_2000;

    @BeforeEach
    void setup() {
        product_1000 = product(1000);
        product_2000 = product(2000);
        menuProduct_1000 = new MenuProduct(product_1000, 3);
        menuProduct_2000 = new MenuProduct(product_2000, 3);
        menuProducts = new MenuProducts(Arrays.asList(menuProduct_1000, menuProduct_2000));
        menu = new Menu("정상",
                new FakeMenuProfanityPolicy(),
                8000,
                menuGroup(),
                true,
                menuProducts);

    }

    @DisplayName("[성공] 메뉴를 생성한다.")
    @Test
    void create1() {
        //when
        Menu response = new Menu("정상",
                new FakeMenuProfanityPolicy(),
                8000,
                menuGroup(),
                true,
                menuProducts);
        //then
        assertThat(response.getPriceValue())
                .isEqualTo(BigDecimal.valueOf(8000));
    }

    @DisplayName("[실패] 메뉴 생성 - 메뉴 가격은 메뉴 상품 금액의 합보다 작거나 같다.")
    @Test
    void create2() {
        assertThatThrownBy(
                () -> new Menu("정상",
                        new FakeMenuProfanityPolicy(),
                        10000,
                        menuGroup(),
                        true,
                        menuProducts)
        ).isInstanceOf(PriceException.class);
    }

    @DisplayName("[실패] 메뉴 가격 수정 - 메뉴 가격은 메뉴 상품 금액의 합보다 작거나 같다. ")
    @Test
    void changePrice() {
        assertThatThrownBy(() -> menu.changePrice(new Price(10000)))
                .isInstanceOf(PriceException.class);
    }

    @DisplayName("[성공] 메뉴 상품 금액의 합보다 메뉴 가격이 크면, 메뉴는 숨겨진다.")
    @Test
    void changePrice1() {
        //given
        product_2000.changePrice(new Price(500));
        menu.hideWhenPriceGreaterThanProducts();
        //then
        assertThat(menu.isDisplayed()).isFalse();
    }

    @DisplayName("[성공] 메뉴를 숨긴다.")
    @Test
    void hide() {
        //when
        menu.hide();
        //then
        assertThat(menu.isDisplayed()).isFalse();
    }

    @DisplayName("[성공] 메뉴를 노출한다.")
    @Test
    void display1() {
        //when
        menu.display();
        //then
        assertThat(menu.isDisplayed()).isTrue();
    }


    @DisplayName("[실패] 메뉴 상품 금액의 합보다 메뉴 가격이 크면, 메뉴를 노출할 수 없다.")
    @Test
    void display2() {
        //given
        product_2000.changePrice(new Price(500));
        //when
        assertThatThrownBy(() -> menu.display())
                .isInstanceOf(PriceException.class);
    }
}
