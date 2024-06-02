package kitchenpos.menus.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TobeMenuGroupTest {


    @Test
    @DisplayName("메뉴 그룹을 생성할 수 있다.")
    void create() {
        // given
        String name = "후라이드 치킨";

        // when
        TobeMenuGroup tobeMenuGroup = TobeMenuGroup.create(name);

        // then
        assertAll(
                () -> assertNotNull(tobeMenuGroup),
                () -> assertNotNull(tobeMenuGroup.getId()),
                () -> assertEquals(name, tobeMenuGroup.getName())
        );
    }

}
