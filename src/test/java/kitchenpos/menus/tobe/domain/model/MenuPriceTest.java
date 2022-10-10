package kitchenpos.menus.tobe.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MenuPriceTest {

    @DisplayName("메뉴 가격은 음수가 될 수 없다.")
    @Test
    void create_NegativePrice() {
        assertThatThrownBy(() -> new MenuPrice(-1000L))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("가격 비교")
    @Test
    void isGreaterThan() {
        assertThat(new MenuPrice(1000L).isGreaterThan(500L)).isTrue();
        assertThat(new MenuPrice(1000L).isGreaterThan(1500L)).isFalse();
    }

    @DisplayName("동등성 비교")
    @Test
    void equals() {
        assertThat(new MenuPrice(1000L)).isEqualTo(new MenuPrice(1000L));
    }
}