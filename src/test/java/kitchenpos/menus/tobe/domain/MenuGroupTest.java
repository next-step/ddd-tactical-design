package kitchenpos.menus.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * MenuGroup은 번호와 이름을 가진다.
 */
class MenuGroupTest {
    @DisplayName("메뉴 그룹 정상 생성")
    @Test
    void create() {
        String name = "후라이드";

        MenuGroup menuGroup = new MenuGroup(name);

        assertThat(menuGroup.getName()).isEqualTo(name);
    }

    @DisplayName("이름이 없거나 부정확 할때 메뉴 그룹 생성 실패")
    @ParameterizedTest
    @NullAndEmptySource
    public void create_fail_by_name(String name) {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new MenuGroup(name));
    }
}
