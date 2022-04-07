package kitchenpos.menus.domain.tobe;

import kitchenpos.support.StubBanWordFilter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class MenuGroupTest {

    @DisplayName("MenuGroup은 식별자와 이름을 가진다.")
    @Test
    void create() {
        final MenuGroup menuGroup = new MenuGroup("두마리메뉴", new StubBanWordFilter(false));

        assertAll(
                () -> assertThat(menuGroup).extracting("id").isNotNull(),
                () -> assertThat(menuGroup).extracting("name").isNotNull()
        );
    }
}
