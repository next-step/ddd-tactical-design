package kitchenpos.menus.tobe.domain.menugroup;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.UUID;
import kitchenpos.common.domain.DisplayedName;
import kitchenpos.common.domain.DisplayedNamePolicy;
import kitchenpos.common.domain.FakeDisplayedNamePolicy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

@DisplayName("메뉴 그룹은")
class MenuGroupTest {

    private final DisplayedNamePolicy policy = new FakeDisplayedNamePolicy();

    @Nested
    @DisplayName("등록할 수 있다.")
    class 등록할_수_있다 {

        @DisplayName("이름이 비어있다면 등록할 수 없다")
        @ParameterizedTest(name = "{0}인 경우")
        @NullAndEmptySource
        void 이름이_비어있다면_등록할_수_없다(String value) {
            assertThatIllegalArgumentException()
                .isThrownBy(() -> new MenuGroup(UUID.randomUUID(), new DisplayedName(value, policy)));
        }

        @DisplayName("이름이 비어있지 않다면 등록할 수 있다")
        @Test
        void 이름이_비어있지_않다면_등록할_수_있다() {
            assertDoesNotThrow(() -> new MenuGroup(UUID.randomUUID(), new DisplayedName("test", policy)));
        }
    }
}
