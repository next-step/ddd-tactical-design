package kitchenpos.menus.tobe.domain.model;

import kitchenpos.common.domain.Price;
import kitchenpos.menus.tobe.domain.fixture.MenuFixture;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MenuTest {

    @DisplayName("생성 검증")
    @Test
    void create() {
        Assertions.assertDoesNotThrow(() -> MenuFixture.MENU_FIXTURE(19000L, "두마리후라이드", 10000L, 2L));
    }

    @DisplayName("생성시 노출여부 검증")
    @Test
    void isDisplayWhenCreate() {
        Menu menu1 = MenuFixture.MENU_FIXTURE(19000L, "두마리후라이드", 10000L, 2L);
        assertThat(menu1.isDisplayed()).isTrue();

        Menu menu2 = MenuFixture.MENU_FIXTURE(21000L, "두마리후라이드", 10000L, 2L);
        assertThat(menu2.isDisplayed()).isFalse();

    }

    @DisplayName("비정상적 생성시 에러 검증 - 메뉴에 속한 상품의 수량은 1 이상이어야 한다.")
    @Test
    void invalidQuantityCreate() {
        assertThatThrownBy(() -> MenuFixture.MENU_FIXTURE(21000L, "두마리후라이드", 10000L, 0L));
    }

    @DisplayName("비정상적 생성시 에러 검증 - 메뉴의 가격이 올바르지 않으면 등록할 수 없다(메뉴의 가격은 0원 이상이어야 한다)")
    @Test
    void invalidPriceCreate() {
        assertThatThrownBy(() -> MenuFixture.MENU_FIXTURE(-1L, "두마리후라이드", 10000L, 2L));
    }

    @DisplayName("비정상적 생성시 에러 검증 - 메뉴의 이름이 올바르지 않으면 등록할 수 없다(메뉴의 이름에는 비속어가 포함될 수 없다)")
    @Test
    void invalidNameCreate() {
        assertThatThrownBy(() -> MenuFixture.MENU_FIXTURE(19000L, "비속어", 10000L, 2L));
    }

    @DisplayName("가격 변경 검증")
    @Test
    void changePrice() {
        Menu menu = MenuFixture.MENU_FIXTURE(19000L, "두마리후라이드", 10000L, 2L);
        menu.changePrice(BigDecimal.valueOf(18000L));
        Price newPrice = new Price(BigDecimal.valueOf(18000L));

        org.assertj.core.api.Assertions.assertThat(menu.getPrice().equals(newPrice))
                .isTrue();
    }

    @DisplayName("비정상적 가격 변경시 에러 검증")
    @Test
    void invalidChangePrice() {
        Menu menu = MenuFixture.MENU_FIXTURE(19000L, "두마리후라이드", 10000L, 2L);
        assertThatThrownBy(() -> menu.changePrice(BigDecimal.valueOf(-18000L)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴 숨김 검증")
    @Test
    void display() {
        Menu menu = MenuFixture.MENU_FIXTURE(19000L, "두마리후라이드", 10000L, 2L);

        assertThat(menu.isDisplayed()).isTrue();
        menu.hide();

        assertThat(menu.isDisplayed()).isFalse();
    }

    @DisplayName("비정상적 노출 시도시 에러 검증 - 메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 높을 경우 메뉴를 노출할 수 없다.")
    @Test
    void invalidDisplay() {
        Menu menu = MenuFixture.MENU_FIXTURE(21000L, "두마리후라이드", 10000L, 2L);

        assertThatThrownBy(menu::display)
                .isInstanceOf(IllegalStateException.class);
    }

}
