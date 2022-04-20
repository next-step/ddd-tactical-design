package kitchenpos.menus.tobe.menu;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MenuPriceTest {
    @Test
    @DisplayName("메뉴의 가격이 0원 이상이어야 한다")
    void constructor01() {
        MenuPrice price = new MenuPrice(0);

        assertThat(price).isEqualTo(new MenuPrice(0));
    }

    @Test
    @DisplayName("메뉴의 가격이 0원 미만일 수 없다")
    void constructor02() {
        assertThatThrownBy(() ->
                new MenuPrice(-1)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("가격 비교 입력한 가격보다 크면 참을 반환한다")
    @Test
    void isGreaterThan01() {
        assertThat(new MenuPrice(10).isGreaterThan(9)).isTrue();
    }

    @DisplayName("가격 비교 입력한 가격하고 같거나 작으면 거짓을 반환한다")
    @ParameterizedTest
    @ValueSource(longs = {11, 10})
    void isGreaterThan01(long other) {
        assertThat(new MenuPrice(10).isGreaterThan(other)).isFalse();
    }
}
