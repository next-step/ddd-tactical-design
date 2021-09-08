package kitchenpos.menus.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static kitchenpos.menus.tobe.domain.fixtures.MenuGroupFixture.DEFAULT_MENU_GROUP;
import static kitchenpos.menus.tobe.domain.fixtures.MenuGroupFixture.MENU_GROUP_WITH_NAME;
import static org.assertj.core.api.Assertions.assertThat;

class MenuGroupTest {

    @DisplayName("메뉴 그룹은 식별자을 반환한다.")
    @Test
    void getId() {
        final MenuGroup menuGroup = DEFAULT_MENU_GROUP();

        assertThat(menuGroup.getId()).isNotNull();
    }

    @DisplayName("메뉴 그룹은 이름을 반환한다.")
    @ValueSource(strings = "추천메뉴")
    @ParameterizedTest
    void getName(final String name) {
        final MenuGroup menuGroup = MENU_GROUP_WITH_NAME(name);

        assertThat(menuGroup.getName()).isEqualTo(name);
    }
}
