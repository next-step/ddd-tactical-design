package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DisplayedNameTest {
    private final Profanity profanity = new FakePurgomalumClient();

    @Test
    @DisplayName("이름을 지을수 있다.")
    void name() {
        DisplayedName displayedName1 = new DisplayedName("상점 제목", profanity);
        DisplayedName displayedName2 = new DisplayedName("상점 제목", profanity);

        assertThat(displayedName1).isEqualTo(displayedName2);
    }

    @Test
    @DisplayName("이름은 비어있을 수 없다.")
    void name_empty() {
        assertThatThrownBy(() -> new DisplayedName("", profanity))
                .isInstanceOf(IllegalArgumentException.class);
    }


    @ParameterizedTest
    @ValueSource(strings = {"비속어", "욕설이 포함된 이름"})
    @DisplayName("이름이 비속어가 포함될 수 없다.")
    void name_profanity(String name) {
        assertThatThrownBy(() -> new DisplayedName(name, profanity))
                .isInstanceOf(IllegalArgumentException.class);
    }
}