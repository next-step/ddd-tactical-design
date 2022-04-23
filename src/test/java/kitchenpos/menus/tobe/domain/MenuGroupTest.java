package kitchenpos.menus.tobe.domain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.UUID;

import org.junit.jupiter.api.Test;

class MenuGroupTest {

    @Test
    void 메뉴그룹을_생성할수_있다() {
        //given
        final String name = "메뉴그룹";
        //when
        //then
        assertDoesNotThrow(() -> new MenuGroup(UUID.randomUUID(), name));
    }

}
