package kitchenpos.menus.tobe.domain.vo;

import kitchenpos.menus.tobe.domain.FakeProfanitiesClient;
import kitchenpos.menus.tobe.domain.exception.InvalidMenuNameException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

class MenuNameTest {

    private Profanities profanities;

    @BeforeEach
    void setUp() {
        profanities = new DefaultProfanities();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"", " ", "   "})
    void 메뉴_이름이_존재해야_한다(String invalidMenuName) {
        // given & when & then
        assertThatThrownBy(() -> new MenuName(invalidMenuName, profanities))
                .isInstanceOf(InvalidMenuNameException.class)
                .hasMessage("메뉴 이름이 존재해야 합니다.");
    }

    @Test
    void 메뉴이름에_비속어가_포함되면_안된다() {
        // given
        Profanities profanities;
        profanities = new FakeProfanitiesClient(List.of("욕설", "비속어"));

        // when & then
        assertThatThrownBy(() -> new MenuName("욕설", profanities))
                .isInstanceOf(InvalidMenuNameException.class)
                .hasMessage("메뉴 이름에는 비속어가 포함되면 안됩니다.");

        assertThatThrownBy(() -> new MenuName("비속어", profanities))
                .isInstanceOf(InvalidMenuNameException.class)
                .hasMessage("메뉴 이름에는 비속어가 포함되면 안됩니다.");
    }
}
