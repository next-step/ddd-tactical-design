package kitchenpos.menus.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class MenuGroupTest {

    @Test
    @DisplayName("성공")
    void success() {
        //given
        String name = "치킨류";

        //when
        MenuGroup menuGroup = new MenuGroup(name);

        //then
        assertThat(menuGroup.getName()).isEqualTo(name);
    }


}
