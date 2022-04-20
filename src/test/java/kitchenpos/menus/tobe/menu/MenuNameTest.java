package kitchenpos.menus.tobe.menu;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MenuNameTest {
    @Test
    @DisplayName("메뉴의 이름은 1자 이상이어야 한다")
    void constructor01() {
        MenuName name = new MenuName("인기 메뉴");

        assertThat(name).isEqualTo(new MenuName("인기 메뉴"));
    }

    @DisplayName("메뉴의 이름은 공백일 수 없다")
    @ParameterizedTest
    @NullAndEmptySource
    void constructor02(String value) {
        assertThatThrownBy(() ->
                new MenuName(value)
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
