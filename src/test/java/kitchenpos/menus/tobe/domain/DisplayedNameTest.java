package kitchenpos.menus.tobe.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

@DisplayName("전시이름 테스트")
class DisplayedNameTest {

    @DisplayName("전시이름을 생성할 수 있다.")
    @Test
    void createName() {
        DisplayedName name = DisplayedName.from("점심특선");
        Assertions.assertThat(name).isEqualTo(DisplayedName.from("점심특선"));
    }

    @DisplayName("전시이름을 없거나 비어있을 수 없다")
    @ParameterizedTest
    @NullAndEmptySource
    void createName_notValidName(String value) {
        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(() -> DisplayedName.from(value));
    }
}