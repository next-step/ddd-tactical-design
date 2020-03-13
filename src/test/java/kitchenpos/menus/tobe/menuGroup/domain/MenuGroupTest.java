package kitchenpos.menus.tobe.menuGroup.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MenuGroupTest {

    @DisplayName("메뉴 그룹을 생성할 수 있다.")
    @Test
    void create() {
        // given
        final String name = "메뉴그룹";

        // when
        final MenuGroup menuGroup = new MenuGroup(name);

        // then
        assertThat(menuGroup.getName()).isEqualTo(name);
    }

    @DisplayName("메뉴그룹 생성 시, 메뉴그룹명을 입력해야한다.")
    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {""})
    void createFailsWhenNameIsBlank(String name) {
        // given
        // when
        // then
        assertThatThrownBy(() -> {
            new MenuGroup(name);
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
