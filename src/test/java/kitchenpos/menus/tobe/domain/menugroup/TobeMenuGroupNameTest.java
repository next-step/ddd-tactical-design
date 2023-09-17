package kitchenpos.menus.tobe.domain.menugroup;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TobeMenuGroupNameTest {

    @DisplayName("메뉴그룹이름을 생성한다.")
    @Test
    void create01() {
        TobeMenuGroupName menuGroupName = new TobeMenuGroupName("두마리메뉴");

        assertThat(menuGroupName).isEqualTo(new TobeMenuGroupName("두마리메뉴"));
    }

    @DisplayName("메뉴그룹이름은 비어있을 수 없다.")
    @Test
    void create02() {
        assertThatThrownBy(() -> new TobeMenuGroupName(null))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
