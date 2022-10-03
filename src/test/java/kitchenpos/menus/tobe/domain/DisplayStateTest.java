package kitchenpos.menus.tobe.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("전시 여부 테스트")
class DisplayStateTest {

    @DisplayName("공개된 상태를 생성할 수 있다.")
    @Test
    void show() {
        DisplayState shown = DisplayState.show();
        Assertions.assertThat(shown).isEqualTo(DisplayState.show());
    }

    @DisplayName("숨겨진 상태를 생성할 수 있다.")
    @Test
    void hide() {
        DisplayState hidden = DisplayState.hide();
        Assertions.assertThat(hidden).isEqualTo(DisplayState.hide());
    }
}