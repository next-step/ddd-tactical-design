package kitchenpos.menus.tobe.domain.model;

import kitchenpos.commons.tobe.domain.model.DisplayedName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class MenuGroupTest {

    @DisplayName("메뉴 그룹을 생성한다.")
    @Test
    void createMenuGroup() {
        final DisplayedName displayName = new DisplayedName("추천메뉴");

        final MenuGroup menuGroup = new MenuGroup(UUID.randomUUID(), displayName);

        assertAll(
                () -> assertThat(menuGroup.getId()).isNotNull(),
                () -> assertThat(menuGroup.getName()).isEqualTo(displayName.value())
        );
    }
}
