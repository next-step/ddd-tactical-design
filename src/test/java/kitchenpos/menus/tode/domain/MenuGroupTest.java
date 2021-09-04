package kitchenpos.menus.tode.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.UUID;

import static kitchenpos.menus.tode.domain.Fixtures.MENU_GROUP_WITH_ID;
import static kitchenpos.menus.tode.domain.Fixtures.MENU_GROUP_WITH_NAME;
import static org.assertj.core.api.Assertions.assertThat;

class MenuGroupTest {

    @DisplayName("메뉴 그룹은 식별자을 반환한다.")
    @Test
    void getId() {
        final UUID expected = UUID.randomUUID();
        final MenuGroup menuGroup = MENU_GROUP_WITH_ID(expected);

        assertThat(menuGroup.getId()).isEqualTo(expected);
    }

    @DisplayName("메뉴 그룹은 이름을 반환한다.")
    @ValueSource(strings = "추천메뉴")
    @ParameterizedTest
    void getName(final String name) {
        final MenuGroup menuGroup = MENU_GROUP_WITH_NAME(name);

        assertThat(menuGroup.getName()).isEqualTo(name);
    }
}
