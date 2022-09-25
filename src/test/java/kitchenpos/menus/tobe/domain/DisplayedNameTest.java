package kitchenpos.menus.tobe.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import kitchenpos.products.tobe.domain.DisplayedName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class DisplayedNameTest {
    @DisplayName("성공 테스트")
    @Nested
    class SuccessTest {
        @DisplayName("유효한 이름일 경우 정상적으로 객체가 생성된다.")
        @Test
        void create() {
            // given
            String name = "양념치킨";

            // when
            DisplayedName displayedName = new DisplayedName(name);

            // then
            assertThat(displayedName.getValue()).isEqualTo(name);
        }
    }

    @DisplayName("실패 테스트")
    @Nested
    public class FailTest {
        @DisplayName("이름이 null 일 경우 예외가 발생한다.")
        @Test
        void nullName() {
            assertThatIllegalArgumentException().isThrownBy(() -> new DisplayedName(null));
        }
    }
}
