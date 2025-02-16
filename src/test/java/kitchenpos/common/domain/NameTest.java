package kitchenpos.common.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class NameTest {

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("이름은 비어있거나, null일 수 없다.")
    void create_name_exception(String name) {
        // when // then
        assertThatThrownBy(() -> new Name(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름을 채워주세요!");
    }
}
