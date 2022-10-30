package kitchenpos.common;

import kitchenpos.menus.tobe.domain.exception.ProfaneNameException;
import kitchenpos.doubles.FakeProfanity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DisplayedNameTest {

    Profanity profanity = new FakeProfanity();

    @ParameterizedTest(name = "메뉴 이름은 비어있을 수 없다.")
    @NullAndEmptySource
    void create_Empty(String name) {
        assertThatThrownBy(() -> new DisplayedName(profanity, name))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest(name = "메뉴 이름은 비속어를 포함할 수 없다.")
    @ValueSource(strings = {"바보 치킨", "멍청이 골뱅이"})
    void create_Profanity(String name) {
        assertThatThrownBy(() -> new DisplayedName(profanity, name))
                .isInstanceOf(ProfaneNameException.class);
    }

    @DisplayName("동등성 비교")
    @Test
    void equals() {
        assertThat(new DisplayedName(profanity, "치킨"))
                .isEqualTo(new DisplayedName(profanity, "치킨"));
    }
}
