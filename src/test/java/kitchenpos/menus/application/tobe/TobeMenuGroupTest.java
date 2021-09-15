package kitchenpos.menus.application.tobe;

import kitchenpos.menus.tobe.domain.TobeMenuGroup;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class TobeMenuGroupTest {

    @DisplayName("메뉴 그룹 생성")
    @Test
    void create() {
        //given
        TobeMenuGroup tobeMenuGroup = new TobeMenuGroup("메뉴그룹");

        //then
        assertThat(tobeMenuGroup).isNotNull();
    }
}
