package kitchenpos.menus.tobe.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class MenuGroupTest {
    @DisplayName("성공 테스트")
    @Nested
    class SuccessTest {
        @DisplayName("유효한 이름일 경우 정상적으로 객체가 생성된다.")
        @Test
        void create() {
            // given
            String name = "두마리치킨";

            // when
            MenuGroup menuGroup = new MenuGroup(name);

            // then
            assertThat(menuGroup.getName()).isEqualTo(name);
        }
    }

    @DisplayName("실패 테스트")
    @Nested
    public class FailTest {
        @DisplayName("이름이 null 또는 빈 문자열 일 경우 예외가 발생한다.")
        @ParameterizedTest
        @NullAndEmptySource
        void nullAndEmptyName(String name) {
            assertThatIllegalArgumentException().isThrownBy(() -> new MenuGroup(name));
        }
    }
}
