package kitchenpos.menus.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class MenuGroupTest {
    @DisplayName("메뉴 그룹을 생성한다.")
    @Test
    void create() {
        assertDoesNotThrow(() -> new MenuGroup("추천 메뉴"));
    }
}
