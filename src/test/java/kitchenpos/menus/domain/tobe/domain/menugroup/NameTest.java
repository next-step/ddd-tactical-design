package kitchenpos.menus.domain.tobe.domain.menugroup;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class NameTest {

    @DisplayName("메뉴 그룹의 이름을 생성할 수 있다.")
    @Test
    void 생성() {
        assertDoesNotThrow(
            () -> new Name("두마리메뉴")
        );
    }

    @DisplayName("메뉴 그룹의 이름은 비워둘 수 없다.")
    @NullAndEmptySource
    @ParameterizedTest
    void 빈이름(final String name) {
        assertThatThrownBy(
            () -> new Name(name)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴 그룹 이름 간 동등성을 확인할 수 있다.")
    @Test
    void 동등성() {
        final Name name1 = new Name("두마리메뉴");
        final Name name2 = new Name("두마리메뉴");

        assertThat(name1).isEqualTo(name2);
    }
}
