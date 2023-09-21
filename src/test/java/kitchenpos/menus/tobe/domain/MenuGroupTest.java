package kitchenpos.menus.tobe.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MenuGroupTest {

    @ParameterizedTest
    @NullAndEmptySource
    void MenuGroup은_식별자와_이름을_갖는다(String nullAndEmpty) {
        assertThatThrownBy(() -> new MenuGroup(UUID.randomUUID(), nullAndEmpty))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(String.format("이름은 없거나 빈 값일 수 없습니다. 현재 값: %s", nullAndEmpty));
    }

    @Test
    void MenuGroup_동등성_비교() {
        UUID menuGroupId = UUID.randomUUID();
        MenuGroup actual = new MenuGroup(menuGroupId, "오늘의 메뉴");

        assertThat(actual.equals(actual)).isTrue();

        assertThat(actual.equals(null)).isFalse();
        assertThat(actual.equals(5)).isFalse();

        assertThat(actual.equals(new MenuGroup(menuGroupId, "오늘의 메뉴"))).isTrue();
        assertThat(actual.equals(new MenuGroup(menuGroupId, "주간 인기 메뉴"))).isFalse();
        assertThat(actual.equals(new MenuGroup(UUID.randomUUID(), "주간 인기 메뉴"))).isFalse();
    }

    @Test
    void MenuGroup_hashCode() {
        UUID menuGroupId = UUID.randomUUID();
        MenuGroup actual = new MenuGroup(menuGroupId, "오늘의 메뉴");

        assertThat(actual.hashCode()).isEqualTo(new MenuGroup(menuGroupId, "오늘의 메뉴").hashCode());
        assertThat(actual.hashCode()).isNotEqualTo(new MenuGroup(UUID.randomUUID(), "주간 인기 메뉴").hashCode());
    }
}
