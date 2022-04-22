package kitchenpos.menus.tobe.menugroup;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MenuGroupNameTest {
    @Test
    @DisplayName("메뉴 그룹의 이름은 1자 이상이어야 한다")
    void constructor01() {
        MenuGroupName name = new MenuGroupName("인기 메뉴");

        assertThat(name).isEqualTo(new MenuGroupName("인기 메뉴"));
    }

    @DisplayName("메뉴 그룹의 이름은 공백일 수 없다")
    @ParameterizedTest
    @NullAndEmptySource
    void constructor02(String value) {
        assertThatThrownBy(() ->
                new MenuGroupName(value)
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
