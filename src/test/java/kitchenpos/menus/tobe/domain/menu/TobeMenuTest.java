package kitchenpos.menus.tobe.domain.menu;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static kitchenpos.TobeFixtures.menu;
import static kitchenpos.TobeFixtures.menuProduct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TobeMenuTest {

    @DisplayName("메뉴 노출")
    @Test
    void display() {
        TobeMenu menu = menu(19_000L, false, menuProduct());
        menu.display();

        assertThat(menu.isDisplayed()).isTrue();
    }

    @DisplayName("메뉴 숨김")
    @Test
    void hide() {
        TobeMenu menu = menu(19_000L, false, menuProduct());
        menu.hide();

        assertThat(menu.isDisplayed()).isFalse();
    }

    @DisplayName("가격 변경")
    @Test
    void updatePrice() {
        TobeMenu menu = menu(19_000L, false, menuProduct());
        menu.updatePrice(new MenuPrice(BigDecimal.valueOf(20_000L)));

        assertThat(menu.getPrice()).isEqualTo(new MenuPrice(BigDecimal.valueOf(20_000L)));
    }

    @DisplayName("가격 변경 시 상품 금액의 합보다 메뉴의 가격이 작을 수 없다.")
    @Test
    void updatePrice01() {
        TobeMenu menu = menu(19_000L, false, menuProduct());
        assertThatThrownBy(() -> menu.updatePrice(new MenuPrice(BigDecimal.valueOf(65_000L))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 합니다.");
    }
}
