package kitchenpos.menus.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class MenuGroupTest {

    @DisplayName("메뉴 그룹을 생성할 수 있다.")
    @Test
    void constructor() {
        assertDoesNotThrow(() -> new MenuGroup("오늘의 메뉴"));
    }

    @DisplayName("메뉴 그룹의 이름이 올바르지 않으면 등록할 수 없다.")
    @ParameterizedTest
    @NullAndEmptySource
    void invalidName(String name) {
        assertThatThrownBy(() -> new MenuGroup(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("메뉴 그룹의 이름으로 사용할 수 없습니다.");
    }
}
