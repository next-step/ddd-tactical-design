package kitchenpos.domain.menu.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class MenuNameTest {

    @DisplayName("메뉴 이름을 생성한다")
    @Test
    void constructor() {
        String name = "메뉴A";
        assertThat(new MenuName(name)).isEqualTo(new MenuName(name));
    }

    @DisplayName("메뉴 이름이 Null이면 생성을 실패한다")
    @Test
    void constructor_null_fail() {
        assertThatIllegalArgumentException().isThrownBy(() -> new MenuName(null));
    }

    @DisplayName("메뉴 이름에 비속어가 포함되면 생성을 실패한다")
    @Test
    void constructor_blackWord_fail() {
        String name = "비속어메뉴";
        assertThatIllegalArgumentException().isThrownBy(() -> new MenuName(name, (text) -> true));
    }
}
