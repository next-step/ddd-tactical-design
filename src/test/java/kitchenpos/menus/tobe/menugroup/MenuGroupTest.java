package kitchenpos.menus.tobe.menugroup;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

class MenuGroupTest {
    @DisplayName("메뉴 그룹은 이름을 가진다.")
    @Test
    void constructor01() {
        assertThatCode(() ->
                new MenuGroup("인기 메뉴")
        ).doesNotThrowAnyException();
    }

    @DisplayName("메뉴 그룹의 이름은 비워 둘 수 없다.")
    @Test
    void constructor02() {
        assertThatThrownBy(() ->
                new MenuGroup("")
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
