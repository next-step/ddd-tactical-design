package kitchenpos.menus.tobe.domain.menu;

import kitchenpos.menus.tobe.application.FakeMenuPurgomalumChecker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class MenuNameTest {

    public PurgomalumChecker purgomalumChecker = new FakeMenuPurgomalumChecker();

    @DisplayName("메뉴 이름 생성")
    @Test
    void create01() {
        MenuName menuName = new MenuName("후라이드치킨", purgomalumChecker);

        assertThat(menuName).isEqualTo(new MenuName("후라이드치킨", purgomalumChecker));
    }

    @DisplayName("메뉴의 이름은 비어 있거을 수 없다.")
    @Test
    void create02() {
        assertThatThrownBy(() -> new MenuName(null, purgomalumChecker))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("메뉴의 이름은 비어있을 수 없습니다.");
    }

    @DisplayName("메뉴의 이름은 비속어가 포함될 수 없다.")
    @Test
    void create03() {
        assertThatThrownBy(() -> new MenuName("비속어 치킨", purgomalumChecker))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("메뉴의 이름은 비속어가 포함될 수 없습니다.");
    }
}
