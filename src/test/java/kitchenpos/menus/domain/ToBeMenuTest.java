package kitchenpos.menus.domain;

import static kitchenpos.menus.domain.MenuFixture.*;
import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import kitchenpos.menus.domain.tobe.domain.MenuPrice;
import kitchenpos.menus.domain.tobe.domain.ToBeMenu;
import kitchenpos.menus.domain.tobe.domain.ToBeMenuProduct;

class ToBeMenuTest {
    private ToBeMenu menu;

    @BeforeEach
    void setUp() {
        menu = createMenu("김밥세트"
            , 30_000L
            , UUID.randomUUID()
            , true
            , false
            , createMenuProduct(30_000L, 1));
        ;
    }

    @DisplayName("상품이 없으면 등록 할 수 없다.")
    @Test
    void notExistsMenuProduct1() {
        assertThatThrownBy(() ->
            createMenuForNullProduct("김밥세트", 8000L)
        )
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("상품이 없으면 등록할 수 없다.");
    }

    @DisplayName("메뉴는 특정 메뉴 그룹에 속해야 한다.")
    @Test
    void nullMenuGroup() {
        assertThatThrownBy(() -> createMenuForNullMenuGroup("김밥세트", 8000L))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("메뉴는 특정 메뉴 그룹에 속해야 한다.");
    }

    @DisplayName("메뉴 이름은 비속어를 가질 수 없다.")
    @Test
    void profanity() {
        assertThatThrownBy(() -> createMenu("김밥세트"
            , 8000L
            , UUID.randomUUID()
            , true
            , true
            , createMenuProduct(8000L, 1)))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("상품 이름에 비속어가 포함되어 있습니다.");
    }

    @DisplayName("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.")
    @Test
    void sumPrice() {
        assertThatThrownBy(() -> createMenu("김밥세트"
            , 8001L
            , UUID.randomUUID()
            , true
            , false
            , createMenuProduct(8000L, 1)))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.");
    }

    @DisplayName("메뉴 가격이 변경된다.")
    @Test
    void changePriceAndDisplay() {
        ToBeMenu menu = createMenu("김밥세트"
            , 30_000L
            , UUID.randomUUID()
            , true
            , false
            , createMenuProduct(30_000L, 1));
        menu.changePrice(BigDecimal.valueOf(29_999L));
        assertThat(menu.getPrice()).isEqualTo(MenuPrice.of(29_999L));
    }

    @DisplayName("메뉴에 속한 상품 금액의 합보다 큰 가격으로 메뉴 가격을 변경하면 안된다.")
    @Test
    void tryChangePrice() {
        assertThatThrownBy(() -> menu.changePrice(BigDecimal.valueOf(30_001)))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.");
    }

    @DisplayName("상품을 노출 시킨다.")
    @Test
    void display1() {
        menu.display();
        assertThat(menu.isDisplayed()).isTrue();
    }

    @DisplayName("메뉴에 속한 상품 금액의 합보다 큰 가격이면 메뉴 노출이 안된다.")
    @Test
    void display2() {
        ToBeMenuProduct menuProduct = new ToBeMenuProduct(UUID.randomUUID(), 30_000L, 1);
        ToBeMenu menu = createMenu("김밥세트"
            , 30_000L
            , UUID.randomUUID()
            , false
            , false
            , menuProduct);
        menuProduct.changePrice(BigDecimal.valueOf(9000L));
        assertThatThrownBy(menu::display)
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.");
    }

    @DisplayName("메뉴가 숨겨진다.")
    @Test
    void hide() {
        menu.hide();
        assertThat(menu.isDisplayed()).isFalse();
    }
}
