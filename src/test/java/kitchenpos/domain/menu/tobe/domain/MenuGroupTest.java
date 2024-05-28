package kitchenpos.domain.menu.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class MenuGroupTest {

    @DisplayName("메뉴 그룹 이름이 Null 혹은 빈 값이 아니면 생성을 성공한다")
    @Test
    void constructor() {
        String name = "메뉴그룹A";
        MenuGroup menuGroup = new MenuGroup(name);
        assertThat(menuGroup.name()).isEqualTo(name);
    }

    @DisplayName("메뉴 그룹 이름이 Null 혹은 빈 값이면 생성을 실패한다")
    @NullAndEmptySource
    @ParameterizedTest
    void constructor_fail(String name) {
        assertThatIllegalArgumentException().isThrownBy(() -> new MenuGroup(name));
    }
}
