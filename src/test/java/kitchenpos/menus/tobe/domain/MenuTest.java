package kitchenpos.menus.tobe.domain;

import kitchenpos.common.FakeProfanities;
import kitchenpos.common.domain.Name;
import kitchenpos.common.domain.Price;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MenuTest {

    @DisplayName("생성 검증")
    @Test
    void create() {
        Assertions.assertDoesNotThrow(() -> createMenu("두마리후라이드", 19000L, 10000L, 2L));
    }

    @DisplayName("생성시 노출여부 검증")
    @Test
    void isDisplayWhenCreate() {
        Menu menu1 = createMenu("두마리후라이드", 19000L, 10000L, 2L);
        org.assertj.core.api.Assertions.assertThat(menu1.isDisplayed()).isTrue();

        Menu menu2 = createMenu("두마리후라이드", 21000L, 10000L, 2L);
        org.assertj.core.api.Assertions.assertThat(menu2.isDisplayed()).isFalse();
    }

    @DisplayName("비정상적 생성시 에러 검증")
    @Test
    void invalidCreate() {

        // 메뉴에 속한 상품의 수량은 1 이상이어야 한다.
        assertThatThrownBy(() -> createMenu("두마리후라이드", 19000L, 10000L, 0L)).isInstanceOf(IllegalArgumentException.class);

        // 메뉴의 가격이 올바르지 않으면 등록할 수 없다. -> 메뉴의 가격은 0원 이상이어야 한다.
        assertThatThrownBy(() -> createMenu("두마리후라이드", -19000L, 10000L, 2L)).isInstanceOf(IllegalArgumentException.class);

        // 메뉴의 이름이 올바르지 않으면 등록할 수 없다. -> 메뉴의 이름에는 비속어가 포함될 수 없다.
        assertThatThrownBy(() -> createMenu("비속어", 19000L, 10000L, 2L)).isInstanceOf(IllegalArgumentException.class);

    }

    @DisplayName("가격 변경 검증")
    @Test
    void changePrice() {
        Menu menu = createMenu("두마리후라이드", 19000L, 10000L, 2L);
        menu.changePrice(BigDecimal.valueOf(18000L));
        Price newPrice = new Price(BigDecimal.valueOf(18000L));

        org.assertj.core.api.Assertions.assertThat(menu.getPrice().equals(newPrice))
                .isTrue();
    }

    @DisplayName("비정상적 가격 변경시 에러 검증")
    @Test
    void invalidChangePrice() {
        Menu menu = createMenu("두마리후라이드", 19000L, 10000L, 2L);
        assertThatThrownBy(() -> menu.changePrice(BigDecimal.valueOf(-18000L)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴 숨김 검증")
    @Test
    void display() {
        Menu menu = createMenu("두마리후라이드", 19000L, 10000L, 2L);

        org.assertj.core.api.Assertions.assertThat(menu.isDisplayed()).isTrue();
        menu.hide();

        org.assertj.core.api.Assertions.assertThat(menu.isDisplayed()).isFalse();
    }

    @DisplayName("비정상적 노출 시도시 에러 검증 - 메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 높을 경우 메뉴를 노출할 수 없다.")
    @Test
    void invalidDisplay() {
        Menu menu = createMenu("두마리후라이드", 21000L, 10000L, 2L);

        assertThatThrownBy(menu::display)
                .isInstanceOf(IllegalStateException.class);
    }

    private Menu createMenu(String name, Long priceValue, Long menuProductPriceValue, Long quantityValue) {
        Name displayName = new Name(name, new FakeProfanities());
        Price price = new Price(BigDecimal.valueOf(priceValue));
        MenuProduct menuProduct = new MenuProduct(UUID.randomUUID(), new Price(BigDecimal.valueOf(menuProductPriceValue)), new Quantity(BigDecimal.valueOf(quantityValue)));
        MenuProducts menuProducts = new MenuProducts(menuProduct);
        MenuGroup menuGroup = new MenuGroup(UUID.randomUUID(), "치킨");

        return new Menu(menuGroup, price, displayName, menuProducts);
    }

}

// 메뉴 요구사항의 "메뉴에 속한 상품의 수량은 0 이상이어야 한다." 에서 0 이상은 0개도 된다는 의미라서 이상해서 일단 1로 변경하였다.

// 메뉴 요구사항의 "메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다." 와 메뉴 모델링의 "`Menu`의 가격이 `MenuProducts`의 금액의 합보다 크면 `NotDisplayedMenu`가 된다."
// 가 충돌한다. 메뉴 요구사항에 따르면 메뉴에 메뉴에 속한 상품 금액의 합이 메뉴의 가격보다 작으면 원천적으로 메뉴의 생성 자체를 차단해야 하는 것 같지만,
// 메뉴 모델링을 보면 생성은 허용해주되 단지 노출 상태값만 다르게 해야한다는 의미라서 일단 메뉴 모델링을 따랐다.
