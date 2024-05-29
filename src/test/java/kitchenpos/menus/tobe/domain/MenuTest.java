package kitchenpos.menus.tobe.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MenuTest {

    private MenuGroup menuGroup;
    private MenuProduct menuProduct;
    private MenuProducts menuProducts;

    @BeforeEach
    void setUp(){
        this.menuGroup = new MenuGroup("치킨류");
        this.menuProduct = new MenuProduct(UUID.randomUUID(), 18000, 2);
        this.menuProducts = new MenuProducts(List.of(menuProduct));
    }

    @Test
    @DisplayName("성공")
    void success() {
        //given when
        Menu menu = new Menu("두마리치킨세트", 34000, menuGroup, true, menuProducts);

        //then
        assertThat(menu.getName()).isEqualTo("두마리치킨세트");
        assertThat(menu.getPrice()).isEqualTo(34000);
        assertThat(menu.getMenuGroup()).isEqualTo(menuGroup);
        assertThat(menu.isDisplayed()).isTrue();
        assertThat(menu.getMenuProducts()).isEqualTo(menuProducts);

    }

    @Test
    @DisplayName("메뉴에 속한 상품보다 메뉴 가격이 낮을 수 없다.")
    void canNotChangePriceUnderPrice() {
        //given when
        Menu menu = new Menu("두마리치킨세트", 34000, menuGroup, true, menuProducts);

        //then
        assertThatThrownBy(() -> menu.changePrice(37000))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("메뉴에 속한 상품보다 메뉴 가격이 낮을때는 display 할 수 없다.")
    void canNotChangeDisplayUnderPrice() {
        //given when
        Menu menu = new Menu(UUID.randomUUID(),"두마리치킨세트", 37000, menuGroup, false, menuProducts);

        //then
        assertThatThrownBy(() -> menu.display())
                .isExactlyInstanceOf(IllegalArgumentException.class);

    }
}
