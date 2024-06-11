package kitchenpos.menugroups.domain.tobe;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import kitchenpos.menugroups.domain.tobe.MenuGroup;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("MenuGroup")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class MenuGroupTest {

    @Test
    void 이름이_null인_MenuGroup을_생성하면_예외를_던진다() {
        assertThatThrownBy(() -> new MenuGroup((String) null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 이름이_비어있는_MenuGroup을_생성하면_예외를_던진다() {
        assertThatThrownBy(() -> new MenuGroup(""))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void MenuGroup을_생성한다() {
        MenuGroup actual = new MenuGroup("치킨");

        assertThat(actual.getId()).isNotNull();
    }
}