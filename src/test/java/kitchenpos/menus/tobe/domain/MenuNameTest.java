package kitchenpos.menus.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class MenuNameTest {

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"", " ", "          "})
    @DisplayName("빈 이름으로는 이름 생성 불가능")
    void createNameTest(String value) {
        assertThatIllegalArgumentException().isThrownBy(() -> new MenuName(value, name -> false));
    }
}
