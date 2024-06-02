package kitchenpos.menu.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static kitchenpos.fixture.MenuGroupFixture.createMenuGroup;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MenuGroupTest {

    @Test
    @DisplayName("메뉴 그룹은 식별자, 메뉴 그룹이름을 갖는다.")
    void success() {
        final var menuGroup = new MenuGroup("메뉴그룹이름");

        assertAll(
                "메뉴 그룹 정보 group assertions",
                () -> assertThat(menuGroup).isNotNull(),
                () -> assertThat(menuGroup.getMenuGroupName()).isNotNull()
        );
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("[실패] 메뉴 그룹의 이름은 필수로 입력해야한다.")
    void name(final String input) {

        assertThrows(IllegalArgumentException.class, () -> new MenuGroup(input));
    }
}
