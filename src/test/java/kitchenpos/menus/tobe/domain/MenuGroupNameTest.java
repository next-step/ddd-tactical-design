package kitchenpos.menus.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class MenuGroupNameTest {

    @Test
    @DisplayName("성공")
    void success() {
        //given
        String name = "치킨류";

        //when
        MenuGroupName menuGroupName = new MenuGroupName(name);

        //then
        assertThat(menuGroupName.getValue()).isEqualTo(name);
    }

    @Test
    @DisplayName("null 이거나 비어있을 수 없다.")
    void canNotCreateHaveEmpty(){
        assertThatThrownBy(() -> new MenuGroupName(""))
                .isExactlyInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new MenuGroupName(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

}
