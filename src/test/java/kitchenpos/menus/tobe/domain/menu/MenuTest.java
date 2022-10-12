package kitchenpos.menus.tobe.domain.menu;


import kitchenpos.menus.domain.MenuGroup;
import kitchenpos.menus.tobe.domain.menugroup.MenuGroupName;
import kitchenpos.products.tobe.domain.DisplayedName;
import kitchenpos.products.tobe.domain.Price;
import kitchenpos.products.tobe.domain.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("메뉴")
class MenuTest {

    @DisplayName("상품이 없으면 등록 할 수 없다.")
    @Test
    void createMenu() {
        MenuProducts menuProducts = new MenuProducts();
        assertThatThrownBy(() -> new Menu(new Price(BigDecimal.valueOf(10000)), menuProducts, new MenuGroup(UUID.randomUUID(), new MenuGroupName("메뉴그룹"))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("메뉴 상품을 등록해주세요.");
    }

    @DisplayName("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 작을 수 없다.")
    @Test
    void menuPrice() {
        MenuProducts menuProducts = new MenuProducts();
        menuProducts.add(new MenuProduct(new Product(new DisplayedName("후라이드 치킨", false), new Price(BigDecimal.valueOf(8000))), new Quantity(BigDecimal.ONE)));
        assertThatThrownBy(() -> new Menu(new Price(BigDecimal.valueOf(10000)), menuProducts, new MenuGroup(UUID.randomUUID(), new MenuGroupName("메뉴그룹"))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 작을 수 없습니다.");
    }

    @DisplayName("메뉴를 숨길 수 있다.")
    @Test
    void hideMenu() {
        MenuProducts menuProducts = new MenuProducts();
        menuProducts.add(new MenuProduct(new Product(new DisplayedName("후라이드 치킨", false), new Price(BigDecimal.valueOf(8000))), new Quantity(BigDecimal.ONE)));
        Menu menu = new Menu(new Price(BigDecimal.valueOf(8000)), menuProducts, new MenuGroup(UUID.randomUUID(), new MenuGroupName("메뉴그룹")));
        menu.hide();
        assertThat(menu.isDisplayed()).isFalse();
    }

    @DisplayName("메뉴는 특정 메뉴 그룹에 속해야 한다.")
    @Test
    void belongToMenuGroup() {
        MenuProducts menuProducts = new MenuProducts();
        menuProducts.add(new MenuProduct(new Product(new DisplayedName("후라이드 치킨", false), new Price(BigDecimal.valueOf(8000))), new Quantity(BigDecimal.ONE)));
        assertThatThrownBy(() -> new Menu(new Price(BigDecimal.valueOf(8000)), menuProducts, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("메뉴는 특정 메뉴 그룹에 속해야 한다.");
    }
}
