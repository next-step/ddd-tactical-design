package kitchenpos.menus.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MenuGroupNameTest {

    @Test
    @DisplayName("메뉴 그룹명을 생성한다.")
    void createMenuGroupName() {
        // given
        String name = "메뉴 그룹";

        // when
        MenuGroupName menuGroupName = new MenuGroupName(name);

        // then
        assertThat(menuGroupName).isEqualTo(new MenuGroupName(name));
    }


    @DisplayName("메뉴 그룹의 이름이 올바르지 않으면 등록할 수 없다.")
    @NullSource
    @ParameterizedTest
    void createMenuGroupName(String name) {
        // when
        // then
        assertThatThrownBy(() -> new MenuGroupName(name))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
