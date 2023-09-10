package kitchenpos.menus.domain;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import kitchenpos.menus.domain.tobe.domain.ToBeMenu;
import kitchenpos.menus.domain.tobe.domain.ToBeMenuGroup;
import kitchenpos.menus.domain.tobe.domain.ToBeMenuProduct;
import kitchenpos.products.domain.tobe.domain.Price;
import kitchenpos.products.domain.tobe.domain.ToBeProduct;

class ToBeMenuTest {

    private ToBeMenuGroup menuGroup;
    private ToBeProduct 김밥;
    private ToBeProduct 돈가스;
    private ToBeMenuProduct menuProduct;

    @BeforeEach
    void setUp() {
        menuGroup = new ToBeMenuGroup("특가");
        김밥 = new ToBeProduct("김밥", BigDecimal.valueOf(10_000L), false);
        돈가스 = new ToBeProduct("돈가스", BigDecimal.valueOf(9000), false);
        menuProduct = new ToBeMenuProduct(김밥, 3);
    }

    @DisplayName("상품이 없으면 등록 할 수 없다.")
    @Test
    void notExistsMenuProduct1() {
        assertThatThrownBy(() -> new ToBeMenu("김밥세트"
            , BigDecimal.valueOf(30_000L)
            , menuGroup
            , true
            , false
            , List.of()))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("상품이 없으면 등록할 수 없다.");
    }

    @DisplayName("상품이 없으면 등록 할 수 없다.")
    @Test
    void notExistsMenuProduct2() {
        assertThatThrownBy(() -> new ToBeMenu("김밥세트"
            , BigDecimal.valueOf(30_000L)
            , menuGroup
            , true
            , false
            , null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("상품이 없으면 등록할 수 없다.");
    }

    @DisplayName("메뉴는 특정 메뉴 그룹에 속해야 한다.")
    @Test
    void nullMenuGroup() {
        assertThatThrownBy(() -> new ToBeMenu("김밥세트"
            , BigDecimal.valueOf(30_000L)
            , null
            , true
            , false
            , List.of(menuProduct)))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("메뉴는 특정 메뉴 그룹에 속해야 한다.");
    }

    @DisplayName("메뉴 이름은 비속어를 가질 수 없다.")
    @Test
    void profanity() {
        assertThatThrownBy(() -> new ToBeMenu("김밥세트"
            , BigDecimal.valueOf(30_000L)
            , menuGroup
            , true
            , true
            , List.of(menuProduct)))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("상품 이름에 비속어가 포함되어 있습니다.");
    }

    @DisplayName("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.")
    @Test
    void sumPrice() {
        assertThatThrownBy(() -> new ToBeMenu("김밥세트"
            , BigDecimal.valueOf(30_001L)
            , menuGroup
            , true
            , false
            , List.of(menuProduct)))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.");
    }

    @DisplayName("메뉴 가격이 변경된다.")
    @Test
    void changePriceAndDisplay() {
        ToBeMenu menu = new ToBeMenu("김밥세트"
            , BigDecimal.valueOf(30_000L)
            , menuGroup
            , true
            , false
            , List.of(menuProduct));
        menu.changePrice(BigDecimal.valueOf(29_999L));
        assertThat(menu.getPrice().equals(Price.of(29_999L))).isTrue();
    }

    @DisplayName("메뉴에 속한 상품 금액의 합보다 큰 가격으로 메뉴 가격을 변경하면 안된다.")
    @Test
    void tryChangePrice() {
        ToBeMenu menu = new ToBeMenu("김밥세트"
            , BigDecimal.valueOf(30_000L)
            , menuGroup
            , true
            , false
            , List.of(menuProduct));

        assertThatThrownBy(() -> menu.changePrice(BigDecimal.valueOf(30_001)))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.");
    }

    @DisplayName("상품을 노출 시킨다.")
    @Test
    void display1() {
        ToBeMenu menu = new ToBeMenu("김밥세트"
            , BigDecimal.valueOf(30_000L)
            , menuGroup
            , false
            , false
            , List.of(menuProduct));
        menu.display();
        assertThat(menu.isDisplayed()).isTrue();
    }

    @DisplayName("메뉴에 속한 상품 금액의 합보다 큰 가격이면 메뉴 노출이 안된다.")
    @Test
    void display2() {
        ToBeMenu menu = new ToBeMenu("김밥세트"
            , BigDecimal.valueOf(30_000L)
            , menuGroup
            , false
            , false
            , List.of(menuProduct));
        김밥.changePrice(BigDecimal.valueOf(90_000L));
        assertThatThrownBy(() -> menu.display())
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.");
    }

    @DisplayName("메뉴가 숨겨진다.")
    @Test
    void hide() {
        ToBeMenu menu = new ToBeMenu("김밥세트"
            , BigDecimal.valueOf(30_000L)
            , menuGroup
            , true
            , false
            , List.of(menuProduct));
        menu.hide();
        assertThat(menu.isDisplayed()).isFalse();
    }
}
